package uz.smartmuslim.fanlarakademiyasi.domain.repository.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.AppealDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.UserDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity
import uz.smartmuslim.fanlarakademiyasi.data.local.shp.impl.MySharedPreference
import uz.smartmuslim.fanlarakademiyasi.data.model.AdminMessage
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.model.UserData
import uz.smartmuslim.fanlarakademiyasi.data.remote.api.Api
import uz.smartmuslim.fanlarakademiyasi.data.remote.mapper.Mapper.toAdmin
import uz.smartmuslim.fanlarakademiyasi.data.remote.request.AdminRequest
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.AppealResponse
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.MessageResponse
import uz.smartmuslim.fanlarakademiyasi.data.utils.MessageData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appealDao: AppealDao,
    private val userDao: UserDao,
    private val api: Api,
    private val fireStore: FirebaseFirestore,
    private val shp: MySharedPreference
) : AppRepository {
    override fun getIsFirst(): Flow<Boolean> = flow {
        emit(shp.isFirst)
    }.flowOn(Dispatchers.IO)

    override suspend fun setIsFirst(state: Boolean) {
        shp.isFirst = state
    }

    override suspend fun updateAppeal(appealData: AppealData) {

        appealDao.update(appealData.toEntity())
    }


    override fun getAllUsers(): Flow<List<UserData>> = flow {
        userDao.getAllUsers().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }
    }

    override fun getAllUnreadAppeals(): Flow<List<AppealData>> = flow {
        appealDao.getAllUnreadAppeals().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }.collect()
    }

    override fun getAllReadAppeals(): Flow<List<AppealData>> = flow {
        appealDao.getAllReadAppeals().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }.collect()
    }

    override fun getAllAnsweredAppeals(): Flow<List<AppealData>> = flow {
        appealDao.getAllAnsweredAppeals().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }.collect()
    }

    override fun check() = callbackFlow<Boolean> {

        fireStore.collection("users").get().addOnSuccessListener {
            val admins = it.documents.map { it.toAdmin() }.toList()
            val admin = admins[0]

            if (shp.login == admin.username
                && shp.password == admin.password
            ) {
                trySend(true)
            } else {
                trySend(false)
            }
        }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)

    override fun login(authData: AuthData): Flow<ResultData<String>> =
        callbackFlow<ResultData<String>> {

            if (hasConnection()) {
                fireStore.collection("users").get()
                    .addOnSuccessListener { it ->
                        val admins = it.documents
                            .map {
                                it.toAdmin()
                            }.toList()
                        val admin = admins[0]
                        if (authData.userName == admin.username
                            && authData.password == admin.password
                        ) {
                            shp.login = authData.userName
                            shp.password = authData.password
                            trySend(ResultData.success("Xush kelibsiz admin"))
                        } else {
                            trySend(ResultData.message(MessageData.messageText("Login yoki parol xato")))
                        }
                    }.addOnFailureListener {
                        trySend(ResultData.error(it.fillInStackTrace()))
                    }
                awaitClose {
                }
            } else {
                trySend(ResultData.message(MessageData.messageText("Internet aloqasi yo'q")))
            }
        }
            .catch { emit(ResultData.error(it)) }
            .flowOn(Dispatchers.IO)

    override fun refreshUserData(): Flow<ResultData<Boolean>> = channelFlow {

        val response = api.getAllUsers()

        try {
            val data = response.body()

            when (response.code()) {
                in 200..299 -> {
                    val list = data?.map {
                        it.toEntity()
                    }?.toList()
                    if (list != null) {
                        userDao.insert(list)
                    }
                    send(ResultData.Success(true))
                }
                in 400..499 -> {
                    send(ResultData.Message(MessageData.messageText("Notog'ri so'rov")))
                }
                in 500..599 -> {
                    send(
                        ResultData.Message(
                            MessageData.messageText(
                                response.errorBody().toString()
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            send(ResultData.Error(e))
        }
    }

    override fun refreshAppealData(): Flow<ResultData<Boolean>> = channelFlow {

        val response = api.getAllAppeal()

        try {
            val data = response.body()

            if (data != null) {
                when (response.code()) {
                    in 200..299 -> {

                        val list = data.map { it.toEntity() }.toList()

                        list.map {
                            if (it.answer.isEmpty()) {
                                it.status = 0
                                appealDao.insertIgnore(it)
                            } else {
                                it.status = 2
                                appealDao.insert(it)
                            }
                        }

                        send(ResultData.Success(true))
                    }
                    in 400..499 -> {
                        send(ResultData.Message(MessageData.messageText("Notog'ri so'rov")))
                    }
                    in 500..599 -> {
                        send(
                            ResultData.Message(
                                MessageData.messageText(
                                    response.errorBody().toString()
                                )
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("TTT", "exception + " + e.message.toString())
            send(ResultData.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun sendMessage(
        userId: String,
        message: String,
        messageId: String
    ): Flow<ResultData<String>> =
        channelFlow {
            try {
                val response = api.sendMessage(userId, AdminRequest(message, messageId))
                val data = response.body()

                if (data != null) {
                    when (response.code()) {
                        in 200..299 -> {
                            send(ResultData.Success(data.message))
                        }
                        in 400..499 -> {
                            send(ResultData.Message(MessageData.messageText("Notog'ri so'rov")))
                        }

                        in 500..599 -> {
                            send(
                                ResultData.Message(
                                    MessageData.messageText(
                                        response.errorBody().toString()
                                    )
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("YYY", e.message.toString())
                send(ResultData.Error(e))
            }
        }.flowOn(Dispatchers.IO)
}
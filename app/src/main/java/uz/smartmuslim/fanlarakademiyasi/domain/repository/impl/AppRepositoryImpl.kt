package uz.smartmuslim.fanlarakademiyasi.domain.repository.impl

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.arefbhrn.eprdownloader.EPRDownloader
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.AppealDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.FileDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.UserDao
import uz.smartmuslim.fanlarakademiyasi.data.local.shp.impl.MySharedPreference
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.model.UserData
import uz.smartmuslim.fanlarakademiyasi.data.remote.api.Api
import uz.smartmuslim.fanlarakademiyasi.data.remote.mapper.Mapper.toAdmin
import uz.smartmuslim.fanlarakademiyasi.data.remote.request.AdminRequest
import uz.smartmuslim.fanlarakademiyasi.data.utils.MessageData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import java.io.File
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appealDao: AppealDao,
    private val userDao: UserDao,
    private val fileDao: FileDao,
    private val api: Api,
    private val fireStore: FirebaseFirestore,
    private val shp: MySharedPreference,
    @ApplicationContext private val context: Context
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
    }.flowOn(Dispatchers.IO)

    override fun getAllUnreadAppeals(): Flow<List<AppealData>> = flow {
        appealDao.getAllUnreadAppeals().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }.collect()
    }.flowOn(Dispatchers.IO)

    override fun getAllReadAppeals(): Flow<List<AppealData>> = flow {
        appealDao.getAllReadAppeals().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }.collect()
    }.flowOn(Dispatchers.IO)

    override fun getAllAnsweredAppeals(): Flow<List<AppealData>> = flow {
        appealDao.getAllAnsweredAppeals().map { it ->
            val list = it.map {
                it.toData()
            }.toList()
            emit(list)
        }.collect()
    }.flowOn(Dispatchers.IO)

    override fun getAllFiles(): Flow<ResultData<Boolean>> = channelFlow {
        try {
            val response = api.getAllFiles()

            Log.d("TTT", "getAllFiles = " + response.body())
            val data = response.body()
            if (data != null) {
                when (response.code()) {
                    in 200..299 -> {
                        fileDao.insert(data.map { it.toEntity() })
                        send(ResultData.success(true))
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            send(ResultData.Error(e))
        }
    }.flowOn(Dispatchers.IO)

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
        awaitClose { }
    }.flowOn(Dispatchers.IO)

    override fun isDownloaded(filedata: FileData) = flow {
        if (filedata.isDownload == 1) {
            emit(true)
        } else {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateFIle(fileData: FileData) {
        fileDao.update(fileData.toEntity())
    }

    override fun login(authData: AuthData): Flow<ResultData<String>> =
        callbackFlow<ResultData<String>> {

            if (hasConnection()) {
                fireStore.collection("users").get()
                    .addOnSuccessListener { it ->
                        val admins = it.documents
                            .map {
                                it.toAdmin()
                            }.toList()
                        if (admins.isNotEmpty()) {
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
                            Log.d("GGG", "500..599 ->" + "200..299" + data.message)
                            send(ResultData.Success(data.message))
                        }

                        in 400..499 -> {
                            send(ResultData.Message(MessageData.messageText("Notog'ri so'rov")))
                        }

                        in 500..599 -> {
                            Log.d("GGG", "500..599 ->" + response.errorBody().toString())
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
                Log.d("GGG", "catch -> " + e.message)
                send(ResultData.Error(e))
            }
        }.flowOn(Dispatchers.IO)

    override fun getFileByHashId(hashId: String) = channelFlow<FileData> {
        fileDao.getFileByHashId(hashId).onEach {

            if (it != null) {

                send(it.toData())
            }
        }.collect()
    }.flowOn(Dispatchers.IO)

    override fun downloadFile(imageEntity: FileData): Flow<ResultData<Result>> = callbackFlow {

        EPRDownloader.download(
            convertShortUrlToRealUrl(imageEntity.fileUrl),
            context.filesDir.path,
            "${imageEntity.name}.pdf"
        )
            .setTag(imageEntity.hashId)
            .build()
            .addOnStartOrResumeListener {
                trySend(ResultData.Success(Result.Start))
            }
            .addOnProgressListener {
                trySend(ResultData.Success(Result.Progress(it.currentBytes, it.totalBytes)))
            }.addOnDownloadListener(object : com.arefbhrn.eprdownloader.OnDownloadListener {
                override fun onDownloadComplete() {
                    trySend(ResultData.Success(Result.End(imageEntity.name)))
                }

                override fun onError(error: com.arefbhrn.eprdownloader.Error?) {
                    trySend(
                        ResultData.Success(
                            Result.Error(
                                error?.serverErrorMessage ?: "Unknown error"
                            )
                        )
                    )
                }
            })
            .start()
        awaitClose { }
    }

    fun convertShortUrlToRealUrl(shortUrl: String): String {
        val asteriskCount = shortUrl.count { it == '*' }
        val realUrl = shortUrl.replace("*", "")
        return realUrl.substring(0, realUrl.length - asteriskCount)
    }

    override fun uploadFile(
        userId: String,
        file: File,
        messageId: String
    ): Flow<ResultData<Boolean>> =
        flow<ResultData<Boolean>> {
            val requestFile =
                RequestBody.create(
                    context.contentResolver.getType(file.toUri())
                        ?.let { it.toMediaTypeOrNull() }, file
                )
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            try {
                val response = api.uploadFile(userId, body, messageId)
                when (response.code()) {
                    in 200..299 -> {
                        emit(ResultData.Success(true))
                    }

                    in 400..499 -> {
                        emit(ResultData.Message(MessageData.messageText("Noto'g'ri so'rov")))
                    }

                    in 500..599 -> {
                    }
                }
            } catch (e: java.lang.Exception) {
                emit(ResultData.Error(e))
            }
        }.flowOn(Dispatchers.IO)
}

sealed interface Result {

    object Start : Result
    class End(val filename: String) : Result
    class Progress(val current: Long, val total: Long) : Result
    class Error(val message: String) : Result

}
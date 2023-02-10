package uz.smartmuslim.fanlarakademiyasi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.model.UserData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData

interface AppRepository {

    fun getIsFirst(): Flow<Boolean>

    suspend fun setIsFirst(state: Boolean)

    fun getAllUsers(): Flow<List<UserData>>

    fun getAllUnreadAppeals(): Flow<List<AppealData>>

    fun getAllReadAppeals(): Flow<List<AppealData>>

    fun getAllAnsweredAppeals(): Flow<List<AppealData>>

    fun login(authData: AuthData): Flow<ResultData<String>>

    fun refreshUserData(): Flow<ResultData<Boolean>>

    fun refreshAppealData(): Flow<ResultData<Boolean>>

}
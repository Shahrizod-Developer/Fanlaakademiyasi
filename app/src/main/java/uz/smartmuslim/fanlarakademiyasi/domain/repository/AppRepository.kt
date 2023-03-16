package uz.smartmuslim.fanlarakademiyasi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.FileEntity
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.model.UserData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import java.io.File

interface AppRepository {
    fun getIsFirst(): Flow<Boolean>
    suspend fun setIsFirst(state: Boolean)
    suspend fun updateAppeal(appealData: AppealData)
    fun getAllUsers(): Flow<List<UserData>>
    fun getAllUnreadAppeals(): Flow<List<AppealData>>
    fun getAllReadAppeals(): Flow<List<AppealData>>
    fun getAllAnsweredAppeals(): Flow<List<AppealData>>
    fun login(authData: AuthData): Flow<ResultData<String>>
    fun refreshAppealData(): Flow<ResultData<Boolean>>
    fun sendMessage(userId: String, message: String, messageId: String): Flow<ResultData<String>>
    fun check(): Flow<Boolean>
    fun isDownloaded(filed: FileData): Flow<Boolean>
    fun getAllFiles(): Flow<ResultData<Boolean>>
    suspend fun updateFIle(filed: FileData)
    fun getFileByHashId(hashId: String): Flow<FileData>
    fun downloadFile(filed: FileData): Flow<ResultData<uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.Result>>
    fun uploadFile(userId: String, file: File, messageId:String): Flow<ResultData<Boolean>>
}
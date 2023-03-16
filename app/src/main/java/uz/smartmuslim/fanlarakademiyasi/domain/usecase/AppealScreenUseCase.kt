package uz.smartmuslim.fanlarakademiyasi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import java.io.File

interface AppealScreenUseCase {

    fun uploadFile(userId:String, file: File, messageId:String): Flow<ResultData<Boolean>>
    suspend fun appealRead(appealData: AppealData)
    fun sendMessage(userId: String, message: String, messageId: String): Flow<ResultData<String>>
    suspend fun updateFile(fileEntity: FileData)
    fun getFileByHashId(hashId: String): Flow<FileData>
    fun getAllFiles(): Flow<ResultData<Boolean>>
    fun downloadFile(fileEntity: FileData): Flow<ResultData<uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.Result>>
}
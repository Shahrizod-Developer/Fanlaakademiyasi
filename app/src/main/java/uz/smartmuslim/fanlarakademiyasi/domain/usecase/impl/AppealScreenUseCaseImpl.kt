package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.FileResponse
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.MessageResponse
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.data.utils.flow
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.AppRepositoryImpl
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.Result
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.AppealScreenUseCase
import java.io.File
import javax.inject.Inject

class AppealScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : AppealScreenUseCase {
    override fun uploadFile(
        userId: String,
        file: File,
        messageId: String
    ): Flow<ResultData<Boolean>> =
        repository.uploadFile(userId, file, messageId)

    override suspend fun appealRead(appealData: AppealData) = repository.updateAppeal(appealData)
    override fun sendMessage(
        userId: String,
        message: String,
        messageId: String
    ): Flow<ResultData<String>> =
        repository.sendMessage(userId, message, messageId)

    override fun getAllFiles(): Flow<ResultData<Boolean>> = repository.getAllFiles()

    override suspend fun updateFile(fileData: FileData) = repository.updateFIle(fileData)

    override fun getFileByHashId(hashId: String): Flow<FileData> =
        repository.getFileByHashId(hashId)

    override fun downloadFile(fileData: FileData): Flow<ResultData<Result>> =
        repository.downloadFile(fileData)

}
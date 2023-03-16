package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.AppRepositoryImpl
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.Result
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.FileScreenUseCase
import javax.inject.Inject

class FileScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : FileScreenUseCase {
    override fun isDownloaded(fileData: FileData): Flow<Boolean> = repository.isDownloaded(fileData)

    override fun downloadFile(fileData: FileData): Flow<ResultData<Result>> =
        repository.downloadFile(fileData)

    override suspend fun updateFile(fileData: FileData) = repository.updateFIle(fileData)

    override fun getFilByHashId(hashId: String): Flow<FileData> = repository.getFileByHashId(hashId)
}
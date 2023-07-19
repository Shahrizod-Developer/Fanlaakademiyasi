package uz.smartmuslim.fanlarakademiyasi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.Result

interface FileScreenUseCase {

    fun isDownloaded(fileData: FileData): Flow<Boolean>

    fun downloadFile(fileData: FileData): Flow<ResultData<Result>>

    suspend fun updateFile(fileData: FileData)

    fun getFilByHashId(hashId: String): Flow<FileData>
}
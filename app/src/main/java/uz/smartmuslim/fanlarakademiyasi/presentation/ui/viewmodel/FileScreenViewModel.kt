package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData

interface FileScreenViewModel {

    val message: Flow<String>
    val fileData: Flow<FileData>
    val isDownload: SharedFlow<Boolean>

    fun isDownloaded(fileData: FileData)

    fun downloadFile(fileData: FileData)

    fun openReadFile(fileData: FileData)

    fun updateFile(fileData: FileData)

    fun getFileByHashId(hashId: String)
}
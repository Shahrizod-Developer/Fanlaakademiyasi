package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import java.io.File

interface AppealScreenViewModel {


    val errorMessage: Flow<String>
    val successMessage: Flow<String>
    val fileSentSuccessMessage: Flow<String>
    val message: Flow<String>
    val loading: Flow<Boolean>
    val fileData: Flow<FileData>
    val saveFile: Flow<Boolean>

    fun appealRead(appealData: AppealData)

    fun getFileByHashId(hashId: String)

    fun openSendAnswer(appealData: AppealData)
    fun refreshFiles()

    fun openFileScreen(fileData: FileData)

    fun uploadFile(userId: String, file: File, messageId:String)

    fun sendMessage(userId: String, message: String, messageId: String, context: Context)

}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData

interface AppealScreenViewModel {


    val errorMessage: Flow<String>
    val successMessage: Flow<String>
    val message: Flow<String>
    val loading: Flow<Boolean>

    fun appealRead(appealData: AppealData)

    fun openSendAnswer(appealData: AppealData)

    fun sendMessage(userId: String, message: String, messageId: String, context: Context)
}
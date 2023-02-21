package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.AppealScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.AppealScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.AppealScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.utils.flow
import javax.inject.Inject

@HiltViewModel
class AppealScreenViewModelImpl @Inject constructor(
    private val useCase: AppealScreenUseCase,
    private val direction: AppealScreenDirection
) : AppealScreenViewModel, ViewModel() {

    override val errorMessage = flow<String>()
    override val message = flow<String>()
    override val successMessage = flow<String>()
    override val loading = flow<Boolean>()

    override fun appealRead(appealData: AppealData) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TTT", "Update + " + appealData.status)
            useCase.appealRead(appealData)
        }
    }

    override fun openSendAnswer(appealData: AppealData) {
        viewModelScope.launch {
            direction.openSendAnswerScreen(appealData)
        }
    }

    override fun sendMessage(
        userId: String,
        messages: String,
        messageId: String,
        context: Context
    ) {
        viewModelScope.launch {
            if (hasConnection()) {
                useCase.sendMessage(userId, messages, messageId).collectLatest {
                    when (it) {
                        is ResultData.Success -> {
                            loading.emit(false)
                            successMessage.emit(it.data)
                        }
                        is ResultData.Error -> {
                            loading.emit(false)
                            errorMessage.emit(it.toString())
                        }
                        is ResultData.Message -> {
                            loading.emit(false)
                            message.emit(it.message.getMessageString(context))
                        }
                    }
                }
            } else {
                message.emit("Internetga ulanmagnsiz")
            }

        }
    }
}
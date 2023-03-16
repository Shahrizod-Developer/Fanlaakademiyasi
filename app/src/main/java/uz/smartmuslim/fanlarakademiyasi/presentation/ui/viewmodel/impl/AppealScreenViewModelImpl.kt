package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.AppealScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.AppealScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.AppealScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.utils.flow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AppealScreenViewModelImpl @Inject constructor(
    private val useCase: AppealScreenUseCase,
    private val direction: AppealScreenDirection,
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context
) : AppealScreenViewModel, ViewModel() {

    override val errorMessage = flow<String>()
    override val message = flow<String>()
    override val successMessage = flow<String>()
    override val fileSentSuccessMessage = flow<String>()
    override val loading = flow<Boolean>()
    override val fileData = flow<FileData>()
    override val saveFile = flow<Boolean>()

    override fun getFileByHashId(hashId: String) {
        viewModelScope.launch {
            useCase.getFileByHashId(hashId).collectLatest {
                fileData.emit(it)
            }
        }
    }

    override fun appealRead(appealData: AppealData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.appealRead(appealData)
        }
    }

    override fun openSendAnswer(appealData: AppealData) {
        viewModelScope.launch {
            direction.openSendAnswerScreen(appealData)
        }
    }

    override fun refreshFiles() {
        viewModelScope.launch {
            if (hasConnection()) {
                useCase.getAllFiles().collectLatest {
                }
            } else {
                message.emit("Internet aloqasi mavjud emas")
            }
        }
    }

    override fun openFileScreen(fileData: FileData) {
        viewModelScope.launch {
            direction.openFileScreen(fileData)
        }
    }

    override fun uploadFile(userId: String, file: File, messageId: String) {
        Log.d("JJJ", "viemodel upload + ")
        viewModelScope.launch {
            loading.emit(true)
            useCase.uploadFile(userId, file, messageId).collectLatest { it ->
                it.onSuccess {
                    Log.d("JJJ", "viemodel upload + " + it)
                    loading.emit(false)
                    saveFile.emit(true)
                    fileSentSuccessMessage.emit("Yal yuborildi")
                }.onError {
                    loading.emit(false)
                    message.emit(it.message.toString())
                }.onMessage {
                    loading.emit(false)
                    message.emit(it.getMessageString(context))
                }
            }
        }
    }

    override fun sendMessage(
        userId: String, messages: String, messageId: String, context: Context
    ) {
        viewModelScope.launch {

            if (hasConnection()) {
                loading.emit(true)
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
                message.emit("Internetga ulanmagansiz")
            }
        }
    }
}
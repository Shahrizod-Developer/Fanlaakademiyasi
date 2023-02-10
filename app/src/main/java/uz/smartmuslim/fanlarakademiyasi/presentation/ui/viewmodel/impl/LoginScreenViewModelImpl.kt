package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.adminpanel.domain.usecase.LoginScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.data.utils.flow
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.LoginScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.LoginScreenViewModel
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModelImpl @Inject constructor(
    private val useCase: LoginScreenUseCase,
    private val direction: LoginScreenDirection
) : LoginScreenViewModel, ViewModel() {

    override val errorMessage = flow<String>()
    override val message = flow<String>()
    override val successMessage = flow<String>()
    override val loading = flow<Boolean>()
    override fun onClickSignIn(authData: AuthData, context: Context) {

        viewModelScope.launch {
            loading.emit(true)

            if (authData.userName.length < 4) {
                loading.emit(false)
                message.emit("Login kamida 4 ta belgidan iborat bo'lishi kerak")
            } else if (authData.password.length < 5) {
                loading.emit(false)
                message.emit("Parol kamida 5 ta belgidan iborat bo'lishi kerak")
            } else {
                useCase.login(authData).collectLatest {
                    when (it) {
                        is ResultData.Success -> {
                            loading.emit(false)
                            direction.openMainScreen()
                            successMessage.emit(it.data)
                            useCase.setIsFirst(false)
                        }
                        is ResultData.Error -> {
                            loading.emit(false)
                            message.emit(it.toString())
                        }
                        is ResultData.Message -> {
                            loading.emit(false)
                            message.emit(it.message.getMessageString(context))
                        }
                    }
                }

            }
        }

    }
}

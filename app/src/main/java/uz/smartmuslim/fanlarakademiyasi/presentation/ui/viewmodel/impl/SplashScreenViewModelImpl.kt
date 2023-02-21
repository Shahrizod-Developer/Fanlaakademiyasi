package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.SplashScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.SplashScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.SplashScreenViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor(
    private val useCase: SplashScreenUseCase,
    private val direction: SplashScreenDirection
) : SplashScreenViewModel, ViewModel() {
    override fun openScreen() {

        viewModelScope.launch {
            delay(2000)
            useCase.isFirst().collectLatest {
                if (it) {
                    direction.openLoginScreen()
                } else {
                    if (hasConnection()) {
                        useCase.check().collectLatest {
                            if (it) {
                                direction.openMainScreen()
                            } else {
                                direction.openLoginScreen()
                            }
                        }
                    } else {
                        direction.openLoginScreen()
                    }

                }
            }
        }
    }
}
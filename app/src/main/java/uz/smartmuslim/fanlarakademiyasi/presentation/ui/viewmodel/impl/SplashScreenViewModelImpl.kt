package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.SplashScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.StartScreen
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
            when (useCase.startScreen()) {
                StartScreen.LOGIN -> direction.openLoginScreen()
                StartScreen.PIN -> direction.openMainScreen()
            }
        }
    }
}
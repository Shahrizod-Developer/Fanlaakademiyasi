package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import android.util.Log
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.SplashScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.StartScreen
import javax.inject.Inject

class SplashScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : SplashScreenUseCase {
    override suspend fun startScreen(): StartScreen {
        var screen = StartScreen.PIN

        repository.getIsFirst().map {
            screen = if (it) {
                StartScreen.LOGIN
            } else {
                StartScreen.PIN
            }
        }.collect()

        return screen
    }
}
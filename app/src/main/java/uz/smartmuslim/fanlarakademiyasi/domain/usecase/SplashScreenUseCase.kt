package uz.smartmuslim.fanlarakademiyasi.domain.usecase

interface SplashScreenUseCase {
    suspend fun startScreen(): StartScreen
}

enum class StartScreen {
    PIN, LOGIN
}
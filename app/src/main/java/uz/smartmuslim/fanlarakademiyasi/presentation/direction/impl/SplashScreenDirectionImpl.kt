package uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl

import uz.smartmuslim.fanlarakademiyasi.presentation.direction.SplashScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.splash.SplashScreenDirections
import javax.inject.Inject

class SplashScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : SplashScreenDirection {

    override suspend fun openMainScreen() =
        navigator.navigateTo(SplashScreenDirections.actionSplashScreenToMainScreen())

    override suspend fun openLoginScreen() =
        navigator.navigateTo(SplashScreenDirections.actionSplashScreenToLoginScreen())
}
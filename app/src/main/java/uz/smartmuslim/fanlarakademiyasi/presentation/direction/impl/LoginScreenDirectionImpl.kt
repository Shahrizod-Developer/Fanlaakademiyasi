package uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl

import uz.smartmuslim.fanlarakademiyasi.presentation.direction.LoginScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.auth.LoginScreenDirections
import javax.inject.Inject

class LoginScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : LoginScreenDirection {
    override suspend fun openMainScreen() =
        navigator.navigateTo(LoginScreenDirections.actionLoginScreenToMainScreen())
}
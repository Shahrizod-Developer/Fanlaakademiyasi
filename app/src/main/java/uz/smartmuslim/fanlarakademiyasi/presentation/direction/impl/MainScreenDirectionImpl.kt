package uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl

import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.MainScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.MainScreenDirections
import javax.inject.Inject

class MainScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : MainScreenDirection {
    override suspend fun openAppealScreen(appealData: AppealData) =
        navigator.navigateTo(MainScreenDirections.actionMainScreenToAppealScreen(appealData))
}
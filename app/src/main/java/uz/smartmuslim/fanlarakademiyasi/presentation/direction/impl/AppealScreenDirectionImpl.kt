package uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl

import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.AppealScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer.AppealScreenDirections
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.MainScreenDirections
import javax.inject.Inject

class AppealScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : AppealScreenDirection {
    override suspend fun openSendAnswerScreen(appealData: AppealData) =
        navigator.navigateTo(AppealScreenDirections.actionAppealScreenToSendAnswerScreen(appealData))

    override suspend fun openFileScreen(fileData: FileData) =
        navigator.navigateTo(AppealScreenDirections.actionAppealScreenToFileScreen(fileData))

}
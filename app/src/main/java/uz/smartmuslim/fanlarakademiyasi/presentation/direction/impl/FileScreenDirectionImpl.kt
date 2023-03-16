package uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl

import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.FileScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer.FileScreenDirections
import javax.inject.Inject

class FileScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
): FileScreenDirection {
    override suspend fun openReadFileScreen(fileData: FileData) {
        navigator.navigateTo(FileScreenDirections.actionFileScreenToReadFileScreen(fileData))
    }
}
package uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl

import uz.smartmuslim.fanlarakademiyasi.presentation.direction.FileReadScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator
import javax.inject.Inject

class FileReadScreenDirectionImpl @Inject constructor(
    navigator: Navigator
) : FileReadScreenDirection {
    override suspend fun back() {

    }
}
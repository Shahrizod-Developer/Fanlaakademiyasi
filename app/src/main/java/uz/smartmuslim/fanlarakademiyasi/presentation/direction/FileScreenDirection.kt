package uz.smartmuslim.fanlarakademiyasi.presentation.direction

import uz.smartmuslim.fanlarakademiyasi.data.model.FileData

interface FileScreenDirection {

    suspend fun openReadFileScreen(fileData: FileData)
}
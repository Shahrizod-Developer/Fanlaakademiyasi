package uz.smartmuslim.fanlarakademiyasi.presentation.direction

import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData

interface AppealScreenDirection {

    suspend fun openSendAnswerScreen(appealData: AppealData)

    suspend fun openFileScreen(fileData: FileData)

}
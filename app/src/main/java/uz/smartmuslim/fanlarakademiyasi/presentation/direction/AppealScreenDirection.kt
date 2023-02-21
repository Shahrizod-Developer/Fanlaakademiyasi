package uz.smartmuslim.fanlarakademiyasi.presentation.direction

import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData

interface AppealScreenDirection {

    suspend fun openSendAnswerScreen(appealData: AppealData)

}
package uz.smartmuslim.fanlarakademiyasi.presentation.direction

import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData

interface MainScreenDirection {

    suspend fun openAppealScreen(appealData: AppealData)
}
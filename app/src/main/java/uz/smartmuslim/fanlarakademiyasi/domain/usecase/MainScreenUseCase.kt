package uz.smartmuslim.fanlarakademiyasi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData

interface MainScreenUseCase {

    fun getAllUnreadAppeals(): Flow<List<AppealData>>

    fun getAllReadAppeals(): Flow<List<AppealData>>

    fun getAllAnsweredAppeals(): Flow<List<AppealData>>

    fun refreshAppealData(): Flow<ResultData<Boolean>>


}
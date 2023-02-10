package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.MainScreenUseCase
import javax.inject.Inject

class MainScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
): MainScreenUseCase {

    override fun getAllUnreadAppeals(): Flow<List<AppealData>> = repository.getAllUnreadAppeals()

    override fun getAllReadAppeals(): Flow<List<AppealData>> = repository.getAllReadAppeals()

    override fun getAllAnsweredAppeals(): Flow<List<AppealData>> = repository.getAllAnsweredAppeals()

    override fun refreshAppealData(): Flow<ResultData<Boolean>> = repository.refreshAppealData()
}
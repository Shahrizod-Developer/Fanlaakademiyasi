package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.MessageResponse
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.AppRepositoryImpl
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.AppealScreenUseCase
import javax.inject.Inject

class AppealScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : AppealScreenUseCase {
    override suspend fun appealRead(appealData: AppealData) = repository.updateAppeal(appealData)
    override fun sendMessage(
        userId: String,
        message: String,
        messageId: String
    ): Flow<ResultData<String>> =
        repository.sendMessage(userId, message, messageId)

}
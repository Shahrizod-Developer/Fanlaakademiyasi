package uz.smartmuslim.fanlarakademiyasi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData

interface AppealScreenUseCase {

    suspend fun appealRead(appealData: AppealData)

    fun sendMessage(userId: String, message: String, messageId: String): Flow<ResultData<String>>
}
package uz.smartmuslim.adminpanel.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData

interface LoginScreenUseCase {

    fun login(authData: AuthData): Flow<ResultData<String>>

    suspend fun setIsFirst(state: Boolean)
}
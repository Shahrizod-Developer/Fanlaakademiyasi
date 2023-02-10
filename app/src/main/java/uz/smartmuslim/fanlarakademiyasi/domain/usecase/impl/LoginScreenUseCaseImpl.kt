package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.data.utils.ResultData
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.adminpanel.domain.usecase.LoginScreenUseCase
import javax.inject.Inject

class LoginScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : LoginScreenUseCase {
    override fun login(authData: AuthData): Flow<ResultData<String>> = repository.login(authData)
    override suspend fun setIsFirst(state: Boolean) = repository.setIsFirst(state)
}
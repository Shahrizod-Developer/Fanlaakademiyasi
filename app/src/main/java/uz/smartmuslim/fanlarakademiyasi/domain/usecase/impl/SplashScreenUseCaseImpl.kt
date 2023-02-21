package uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.SplashScreenUseCase
import javax.inject.Inject

class SplashScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : SplashScreenUseCase {
    override fun isFirst(): Flow<Boolean> = repository.getIsFirst()

    override fun check(): Flow<Boolean> = repository.check()
}
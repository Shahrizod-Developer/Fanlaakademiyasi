package uz.smartmuslim.fanlarakademiyasi.domain.usecase

import kotlinx.coroutines.flow.Flow

interface SplashScreenUseCase {

    fun isFirst():Flow<Boolean>
     fun check():Flow<Boolean>
}

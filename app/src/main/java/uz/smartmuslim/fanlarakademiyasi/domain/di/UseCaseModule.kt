package uz.smartmuslim.fanlarakademiyasi.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.smartmuslim.adminpanel.domain.usecase.LoginScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.AppealScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.FileScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.MainScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.SplashScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.impl.*


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindSplashScreenUseCase(impl: SplashScreenUseCaseImpl): SplashScreenUseCase

    @Binds
    fun bindLoginScreenUseCase(impl: LoginScreenUseCaseImpl): LoginScreenUseCase

    @Binds
    fun bindMainScreenUseCase(impl: MainScreenUseCaseImpl): MainScreenUseCase

    @Binds
    fun bindAppealScreenUseCase(impl: AppealScreenUseCaseImpl): AppealScreenUseCase

    @Binds
    fun bindFileScreenUseCase(impl: FileScreenUseCaseImpl): FileScreenUseCase

}
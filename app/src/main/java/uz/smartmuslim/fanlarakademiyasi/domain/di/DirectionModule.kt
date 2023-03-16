package uz.smartmuslim.fanlarakademiyasi.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.*
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl.*


@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds]
    fun bindSplashScreenDirection(impl: SplashScreenDirectionImpl): SplashScreenDirection

    @[Binds]
    fun bindLoginScreenDirection(impl: LoginScreenDirectionImpl): LoginScreenDirection

    @[Binds]
    fun bindMainScreenDirection(impl: MainScreenDirectionImpl): MainScreenDirection

    @[Binds]
    fun bindAppealScreenDirection(impl: AppealScreenDirectionImpl): AppealScreenDirection

    @[Binds]
    fun bindFileScreenDirection(impl: FileScreenDirectionImpl): FileScreenDirection

    @[Binds]
    fun bindReadFileScreenDirection(impl: FileReadScreenDirectionImpl): FileReadScreenDirection
}
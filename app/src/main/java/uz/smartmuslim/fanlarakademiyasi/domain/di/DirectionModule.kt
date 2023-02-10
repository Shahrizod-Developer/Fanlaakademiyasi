package uz.smartmuslim.fanlarakademiyasi.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.LoginScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.MainScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.SplashScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl.LoginScreenDirectionImpl
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl.MainScreenDirectionImpl
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.impl.SplashScreenDirectionImpl


@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds]
    fun bindSplashScreenDirection(impl: SplashScreenDirectionImpl): SplashScreenDirection

    @[Binds]
    fun bindLoginScreenDirection(impl: LoginScreenDirectionImpl): LoginScreenDirection

    @[Binds]
    fun bindMainScreenDirection(impl: MainScreenDirectionImpl): MainScreenDirection
}
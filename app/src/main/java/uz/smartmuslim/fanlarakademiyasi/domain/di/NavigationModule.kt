package uz.smartmuslim.fanlarakademiyasi.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.NavigationDispatcher
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.NavigationHandler
import uz.smartmuslim.fanlarakademiyasi.presentation.navigation.Navigator


@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun navigator(dispatcher: NavigationDispatcher): Navigator

    @Binds
    fun navigatorHandler(dispatcher: NavigationDispatcher): NavigationHandler

}
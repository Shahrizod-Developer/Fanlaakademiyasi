package uz.smartmuslim.fanlarakademiyasi.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.BaseViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.BaseViewModelImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    @Singleton
    fun provideBaseViewModel(impl: BaseViewModelImpl): BaseViewModel = impl

}
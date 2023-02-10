package uz.smartmuslim.fanlarakademiyasi.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.AppRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(impl: AppRepositoryImpl): AppRepository

}
package uz.smartmuslim.fanlarakademiyasi.domain.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.AppealDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.FileDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.dao.UserDao
import uz.smartmuslim.fanlarakademiyasi.data.local.room.database.AppDatabase
import uz.smartmuslim.fanlarakademiyasi.data.local.shp.impl.MySharedPreference
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "new_version").build()

    @[Provides Singleton]
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @[Provides Singleton]
    fun provideAppealDao(appDatabase: AppDatabase): AppealDao = appDatabase.appealDao()

    @[Provides Singleton]
    fun provideFileDao(appDatabase: AppDatabase): FileDao = appDatabase.fileDao()

    @[Provides Singleton]
    fun provideSharedPrefs(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): MySharedPreference =
        MySharedPreference(context, sharedPreferences)

    @[Provides Singleton]
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    @[Provides Singleton]
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore
}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData

interface LoginScreenViewModel {

    val errorMessage: Flow<String>
    val successMessage: Flow<String>
    val message: Flow<String>
    val loading: Flow<Boolean>


    fun onClickSignIn(authData: AuthData, context: Context)
}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow

interface BaseViewModel {

    val loadingSharedFlow: MutableSharedFlow<Boolean>

    val messageSharedFlow: MutableSharedFlow<String>

    val errorSharedFlow: MutableSharedFlow<String>

}
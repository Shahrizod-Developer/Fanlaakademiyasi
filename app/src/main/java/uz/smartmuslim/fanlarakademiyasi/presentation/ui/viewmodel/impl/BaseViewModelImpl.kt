package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import kotlinx.coroutines.flow.MutableSharedFlow
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.BaseViewModel
import javax.inject.Inject


open class BaseViewModelImpl @Inject constructor() : BaseViewModel {

    override val loadingSharedFlow = MutableSharedFlow<Boolean>()

    override val messageSharedFlow = MutableSharedFlow<String>()

    override val errorSharedFlow = MutableSharedFlow<String>()

}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.MainScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.MainScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.MainScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.utils.flow
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val direction: MainScreenDirection,
    private val useCase: MainScreenUseCase
) : MainScreenViewModel, ViewModel() {

    override val message = flow<String>()
    override val unreadAppealList = flow<List<AppealData>>()
    override val readAppealList = flow<List<AppealData>>()
    override val answeredAppealList = flow<List<AppealData>>()

    init {
        viewModelScope.launch {
            useCase.refreshAppealData().collectLatest { it ->
                it.onSuccess {}.onMessage { message.emit(it.toString()) }
                    .onError { message.emit(it.toString()) }
            }
        }
        viewModelScope.launch {
            useCase.getAllUnreadAppeals().collectLatest {
                Log.d("SSS", "viewmodel")
                unreadAppealList.emit(it)
            }
        }
    }
}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData


interface MainScreenViewModel {

    val message: Flow<String>

    val unreadAppealList: Flow<List<AppealData>>
    val readAppealList: Flow<List<AppealData>>
    val answeredAppealList: Flow<List<AppealData>>
}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.domain.repository.AppRepository
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.ReadFileViewModel
import javax.inject.Inject

@HiltViewModel
class ReadFileViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ReadFileViewModel, ViewModel() {
    override fun updateFile(fileData: FileData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFIle(fileData)
        }
    }
}
package uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.data.utils.flow
import uz.smartmuslim.fanlarakademiyasi.data.utils.hasConnection
import uz.smartmuslim.fanlarakademiyasi.domain.usecase.FileScreenUseCase
import uz.smartmuslim.fanlarakademiyasi.presentation.direction.FileScreenDirection
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.BaseViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.FileScreenViewModel
import javax.inject.Inject
import uz.smartmuslim.fanlarakademiyasi.domain.repository.impl.Result

@HiltViewModel
class FileScreenViewModelImpl @Inject constructor(
    private val useCase: FileScreenUseCase,
    private val direction: FileScreenDirection,
    private val baseViewModel: BaseViewModel
) : FileScreenViewModel, ViewModel() {

    override val isDownload = MutableSharedFlow<Boolean>()
    override val message = flow<String>()
    override val fileData = flow<FileData>()

    override fun isDownloaded(fileData: FileData) {
        useCase.isDownloaded(fileData)
        viewModelScope.launch {
            useCase.isDownloaded(fileData).collect {
                isDownload.emit(it)
            }
        }
    }

    override fun downloadFile(fileData: FileData) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasConnection()) {
                useCase.downloadFile(fileData).collectLatest {
                    it.onSuccess { result ->
                        when (result) {
                            is Result.Start -> {
                            }

                            is Result.Progress -> {
                                useCase.updateFile(fileData.copy(download = ((result.current * 100) / result.total).toInt()))
                            }

                            is Result.Error -> {
                                baseViewModel.errorSharedFlow.emit(result.message)
                            }

                            is Result.End -> {
                                useCase.updateFile(
                                    fileData.copy(
                                        isDownload = 1,
                                        fileUrl = result.filename
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun openReadFile(fileData: FileData) {
        viewModelScope.launch {
            direction.openReadFileScreen(fileData)
        }
    }

    override fun updateFile(fileData: FileData) {
        viewModelScope.launch {
            useCase.updateFile(fileData)
        }
    }

    override fun getFileByHashId(hashId: String) {
        viewModelScope.launch {
            useCase.getFilByHashId(hashId).collectLatest {
                fileData.emit(it)
            }
        }
    }
}
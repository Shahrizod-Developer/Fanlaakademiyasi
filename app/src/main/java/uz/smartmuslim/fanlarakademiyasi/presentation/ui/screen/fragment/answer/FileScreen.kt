package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.app.App
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenFileBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.FileScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.FileScreenViewModelImpl
import uz.smartmuslim.fanlarakademiyasi.utils.hasPermissionApp


@AndroidEntryPoint
class FileScreen : Fragment(R.layout.screen_file) {

    private val viewModel: FileScreenViewModel by viewModels<FileScreenViewModelImpl>()
    private val binding: ScreenFileBinding by viewBinding(ScreenFileBinding::bind)
    private val args: FileScreenArgs by navArgs()
    private var isDownload = false

    private val permissionList: List<String> by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
        } else {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var file = args.fileData

        viewModel.getFileByHashId(file.hashId)

        if (file.isDownload == 1) {
            binding.open.visibility = View.VISIBLE
            binding.btnDownloadOrReading.visibility = View.GONE
        } else {
            binding.open.visibility = View.GONE
            binding.btnDownloadOrReading.visibility = View.VISIBLE
        }
        viewModel.isDownload.onEach {
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fileData.collectLatest { fileData ->
                viewModel.isDownloaded(fileData)
                binding.fileName.text = fileData.name
                binding.fileType.text = fileData.extension
                binding.fileSize.text = fileData.fileSize.toString()
                if (fileData.isDownload == 0) {
                    binding.fileState.text = "Yuklanmagan"
                } else binding.fileState.text = "Yuklangan"

                isDownload = fileData.isDownload == 1
                if (isDownload) {
                    binding.progressDownload.visibility = View.INVISIBLE
                    binding.progressText.visibility = View.INVISIBLE
                    binding.btnDownloadOrReading.visibility = View.GONE
                    binding.open.visibility = View.VISIBLE
                    file = fileData.copy()
                } else {
                    binding.progressDownload.progress = fileData.download
                    binding.progressText.text = "${fileData.download} %"
                }
            }
        }

        binding.open.setOnClickListener {
            startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
        }
        binding.btnDownloadOrReading.clicks().debounce(100L)
            .onEach {
                if (file.isDownload == 1) {
                    viewModel.openReadFile(file)
                } else {
                    hasPermissionApp(permissionList) {
                        if (it == 1) {
                            Log.d("TTT", "file url = " + file.fileUrl)
                            viewModel.downloadFile(file)
                            binding.btnDownloadOrReading.text =
                                resources.getString(R.string.downloading)
                            binding.btnDownloadOrReading.isEnabled = false

                            binding.progressDownload.visibility = View.VISIBLE
                            binding.progressText.visibility = View.VISIBLE
                        }
                    }
                }
            }.launchIn(lifecycleScope)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
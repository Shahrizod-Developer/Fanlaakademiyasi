package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.app.App
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenFileBinding
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenReadBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.ReadFileViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.ReadFileViewModelImpl
import java.io.File

@AndroidEntryPoint
class ReadFileScreen :Fragment(R.layout.screen_read){

    private val viewModel: ReadFileViewModel by viewModels<ReadFileViewModelImpl>()
    private val args: ReadFileScreenArgs by navArgs()
    private val binding: ScreenReadBinding by viewBinding(ScreenReadBinding::bind)
    private var maxBook = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.tvBookName.text = args.fileData.name
        binding.tvBookName.setSingleLine()


        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }

        try {
            val uri = FileProvider.getUriForFile(
                requireContext(), "uz.smartmuslim.fanlarakademiyasi" + ".provider",
                File(App.instance.filesDir, args.fileData.name)
            )

            binding.pdfViewer
                .fromUri(uri)
                .defaultPage(1)
                .nightMode(true)
                .pageSnap(true)
                .onPageScroll { page, _ ->
                    if (page > maxBook) {
                        maxBook = page
                        viewModel.updateFile(args.fileData)
                    }
                }
                .load()
        } catch (e: Exception) {
            viewModel.updateFile(
                fileData = args.fileData.copy()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
        }
    }
}
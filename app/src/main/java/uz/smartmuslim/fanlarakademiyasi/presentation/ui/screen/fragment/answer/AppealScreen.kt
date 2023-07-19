package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chinalwb.are.styles.toolitems.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenAppealBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.AppealScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.AppealScreenViewModelImpl
import uz.smartmuslim.fanlarakademiyasi.utils.hasPermissionApp
import uz.smartmuslim.fanlarakademiyasi.utils.toast
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AppealScreen : Fragment(R.layout.screen_appeal) {

    private val binding: ScreenAppealBinding by viewBinding(ScreenAppealBinding::bind)
    private val viewModel: AppealScreenViewModel by viewModels<AppealScreenViewModelImpl>()
    private val appealData: AppealScreenArgs by navArgs()

    var fileUri: Uri? = null

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.refreshFiles()
        var fileData = FileData()
        val data = appealData.appeal
        data.status = 1
        var answer = ""
        answer = if (data.userLang == "UZ") {
            " - sonli murojaatga javob\n"
        } else {
            " - ответ на запрос\n"
        }
        viewModel.getFileByHashId(data.userMessageFileHashId)
        viewModel.appealRead(data)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fullName.text = data.fullName
        binding.address.text = data.address
        binding.time.text = convertLongToTime(data.createDate)
        binding.passportData.text = data.passportData
        binding.phoneNumber.text = data.phoneNumber
        binding.message.text = data.content
        binding.appealNumber.text = data.appealNumber.toString() + " - sonli murojaat"
        binding.birthDate.text = data.birthDate

        when (data.type) {
            "COMPLAINT" -> binding.appealType.text = getString(R.string.complaint)
            "APPLICATION" -> binding.appealType.text = getString(R.string.application)
            "OFFER" -> binding.appealType.text = getString(R.string.offer)
        }
        when (data.recipient) {
            "CORRUPTION_DEPARTMENT" -> binding.recipient.text = getString(R.string.corruption)
            "PERSONAL_DEPARTMENT" -> binding.recipient.text = getString(R.string.personal)
            "MATH_COMPLEX_DEPARTMENT" -> binding.recipient.text = getString(R.string.math_complex)
            "SOCIAL_DEPARTMENT" -> binding.recipient.text = getString(R.string.social)
            "NATURAL_DEPARTMENT" -> binding.recipient.text = getString(R.string.natural)
            "OTHER_DEPARTMENT" -> binding.recipient.text = getString(R.string.other)
        }

        binding.send.setOnClickListener {
            if (binding.description.text.toString()
                    .isNotEmpty() && binding.sendFileName.text == ""
            ) {
                viewModel.sendMessage(
                    data.useId,
                    "" + data.appealNumber + answer + binding.description.text.toString(),
                    data.id,
                    requireContext()
                )
            } else if (binding.description.text.toString()
                    .isNotEmpty() && binding.sendFileName.text.toString() != ""
            ) {
                viewModel.sendMessage(
                    data.useId,
                    "" + data.appealNumber + answer + binding.description.text.toString(),
                    data.id,
                    requireContext()
                )

            } else if (binding.description.text.toString().isEmpty()) {
                toast("Iltimos textni kiriting")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.saveFile.collectLatest {
                if (it) {
                    data.adminMessageFileName = binding.sendFileName.text.toString()
                    viewModel.appealRead(data)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.successMessage.collectLatest {

                fileUri?.let { it1 -> uriToFile(it1) }
                    ?.let { it2 -> viewModel.uploadFile(appealData.appeal.useId, it2, data.id) }

                if (binding.sendFileName.text.toString() == "") {
                    data.status = 2
                    data.answeredTime = System.currentTimeMillis()
                    data.answer = binding.description.text.toString()
                    viewModel.appealRead(data)
                    findNavController().navigate(
                        AppealScreenDirections.actionAppealScreenToSendAnswerScreen(
                            data
                        )
                    )
                }
                toast(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collectLatest {
                if (it) {
                    binding.cl.isEnabled = false
                    binding.cl.alpha = 0.7f
                    binding.loading.visibility = View.VISIBLE
                } else {
                    binding.cl.isEnabled = true
                    binding.cl.alpha = 1f
                    binding.loading.visibility = View.GONE
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fileSentSuccessMessage.collectLatest {
                data.status = 2
                data.answeredTime = System.currentTimeMillis()
                data.answer = binding.description.text.toString()
                viewModel.appealRead(data)
                findNavController().navigate(
                    AppealScreenDirections.actionAppealScreenToSendAnswerScreen(
                        data
                    )
                )
                toast(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collectLatest {
                toast(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collectLatest {
                toast(it)
            }
        }

        if (data.userMessageFileHashId == "") {
            binding.fileName.text = "Fayl biriktirilmagan"
            data.userMessageFileName = ""
        } else {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.fileData.collectLatest {
                    Log.d("TTT", "userMessageFileHashId = " + data.userMessageFileHashId)
                    binding.fileName.text = it.name
                    fileData = it
                    Log.d("TTT", "fileData vi = " + it)
                    data.userMessageFileName = it.name
                    viewModel.appealRead(data)
                }
            }
        }

        binding.upload.setOnClickListener {
            hasPermissionApp(permissionList) {

                if (it == 1) {
                    val intent = Intent()
                    intent.type = "application/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "File tanlang"), 3)
                }
            }
        }

        binding.fileName.setOnClickListener {
            Log.d("TTT", "fileData = " + fileData)
            viewModel.openFileScreen(fileData)
        }

        binding.cancel.setOnClickListener {
            binding.file.visibility = View.GONE
            binding.sendFileName.text = ""
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {

            if (resultCode == RESULT_OK && null != data) {
                val selectedFile = data.data
                fileUri = selectedFile
                binding.sendFileName.text = File(fileUri?.path).name
                binding.file.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    requireContext(), "Siz file tanlamadingiz", Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Nimadir xato ketdi", Toast.LENGTH_LONG).show()
        }
    }

    private fun uriToFile(uri: Uri): File {
        val openInputStream = requireActivity().contentResolver?.openInputStream(uri)

        val type = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(requireContext().contentResolver.getType(uri))
        val file = File(requireActivity().filesDir, binding.sendFileName.text.toString())
        val outputStream = FileOutputStream(file)
        openInputStream?.copyTo(outputStream)
        openInputStream?.close()
        return file
    }
}
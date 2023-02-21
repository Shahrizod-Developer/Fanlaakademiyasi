package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chinalwb.are.AREditText
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import com.chinalwb.are.styles.toolbar.IARE_Toolbar
import com.chinalwb.are.styles.toolitems.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenAppealBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.AppealScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.AppealScreenViewModelImpl
import uz.smartmuslim.fanlarakademiyasi.utils.toast
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AppealScreen : Fragment(R.layout.screen_appeal) {

    private val binding: ScreenAppealBinding by viewBinding(ScreenAppealBinding::bind)
    private val viewModel: AppealScreenViewModel by viewModels<AppealScreenViewModelImpl>()
    private val appealData: AppealScreenArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val data = appealData.appeal
        data.status = 1

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
            if (binding.description.text.toString().isNotEmpty()) {
                viewModel.sendMessage(
                    data.useId,
                    "" + data.appealNumber + " - sonli murojaatga javob\n" + binding.description.text.toString(),
                    data.id,
                    requireContext()
                )
            } else toast("Iltimos Javobni kiriting")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.successMessage.collectLatest {
                toast(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collectLatest {
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
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collectLatest {
                toast(it)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}
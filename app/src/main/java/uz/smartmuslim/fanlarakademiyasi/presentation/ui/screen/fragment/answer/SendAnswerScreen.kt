package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.answer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenSendAnswerBinding
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SendAnswerScreen : Fragment(R.layout.screen_send_answer) {

    private val binding: ScreenSendAnswerBinding by viewBinding(ScreenSendAnswerBinding::bind)
    private val args: SendAnswerScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val data = args.data

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fullName.text = data.fullName
        binding.address.text = data.address
        binding.time.text = convertLongToTime(data.createDate)
        binding.passportData.text = data.passportData
        binding.phoneNumber.text = data.phoneNumber
        binding.message.text = data.content
        binding.birthDate.text = data.birthDate
        binding.answer.text = data.answer
        binding.appealNumber.text = data.appealNumber.toString() + " - sonli murojaat"
        binding.answeredTime.text = convertLongToTime(data.answeredTime)

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

    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}
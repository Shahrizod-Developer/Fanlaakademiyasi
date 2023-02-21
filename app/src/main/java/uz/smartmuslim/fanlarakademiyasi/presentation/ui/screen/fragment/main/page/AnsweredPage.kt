package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.page

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.databinding.PageAnsweredBinding
import uz.smartmuslim.fanlarakademiyasi.databinding.PageReadBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.adapter.AppealAdapter
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.MainScreenDirections
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.MainScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.MainScreenViewModelImpl

@AndroidEntryPoint
class AnsweredPage : Fragment(R.layout.page_answered) {
    private val binding: PageAnsweredBinding by viewBinding(PageAnsweredBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val adapter: AppealAdapter by lazy { AppealAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collectLatest {
                Alerter.create(requireActivity()).setBackgroundColorRes(R.color.bg_circle)
                    .setTitle("Login Message").setText(it).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.answeredAppealList.collectLatest {
                if (it.isEmpty()) {
                    binding.text.visibility = View.VISIBLE
                } else {
                    binding.text.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        }

        binding.rv.adapter = adapter

        adapter.submitOnItemClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToSendAnswerScreen(it))
        }
    }
}
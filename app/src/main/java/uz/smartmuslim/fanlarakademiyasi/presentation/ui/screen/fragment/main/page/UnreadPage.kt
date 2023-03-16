package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.page

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.databinding.PageUnreadBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.adapter.AppealAdapter
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.MainScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.MainScreenViewModelImpl
import uz.smartmuslim.fanlarakademiyasi.utils.toast

@AndroidEntryPoint
class UnreadPage : Fragment(R.layout.page_unread) {

    private val binding: PageUnreadBinding by viewBinding(PageUnreadBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val adapter: AppealAdapter by lazy { AppealAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.refresh()

        binding.refresh.setOnClickListener {
            viewModel.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collectLatest {
                Alerter.create(requireActivity()).setBackgroundColorRes(R.color.bg_circle)
                    .setTitle("Message").setText(it).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.unreadAppealList.collectLatest {
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
            viewModel.onClickItem(it)
        }
    }
}
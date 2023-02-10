package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.auth

import android.os.Bundle
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
import uz.smartmuslim.fanlarakademiyasi.data.model.AuthData
import uz.smartmuslim.fanlarakademiyasi.databinding.ScreenLoginBinding
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.LoginScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.LoginScreenViewModelImpl
import uz.smartmuslim.fanlarakademiyasi.utils.toast

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {

    private val binding: ScreenLoginBinding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginScreenViewModel by viewModels<LoginScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collectLatest {
                Alerter.create(requireActivity()).setBackgroundColorRes(R.color.red)
                    .setTitle("Login Message").setText(it).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.successMessage.collectLatest {
                Alerter.create(requireActivity()).setBackgroundColorRes(R.color.green)
                    .setTitle("Login Message").setText(it).show()
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

        binding.signIn.setOnClickListener {
            viewModel.onClickSignIn(
                AuthData(
                    binding.emailEt.text.toString().trim(),
                    binding.passwordEt.text.toString().trim()
                ),
                requireContext()
            )

        }
    }
}
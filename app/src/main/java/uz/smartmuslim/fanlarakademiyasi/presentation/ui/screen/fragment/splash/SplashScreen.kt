package uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.LoginScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.SplashScreenViewModel
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.LoginScreenViewModelImpl
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.viewmodel.impl.SplashScreenViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openScreen()
    }
}
package com.example.aneon_test_case.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aneon_test_case.utils.EventObserver
import com.example.aneon_test_case.R
import com.example.aneon_test_case.app.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private val viewModel: SplashScreenViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver { destination ->
            viewLifecycleOwner.lifecycleScope.launch {
                delay(1000)
                navController.navigate(destination)
            }
        })

        viewModel.checkUserLoggedIn()
    }
}





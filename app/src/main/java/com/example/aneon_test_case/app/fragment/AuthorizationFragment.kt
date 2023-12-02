package com.example.aneon_test_case.app.fragment

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aneon_test_case.R
import com.example.aneon_test_case.app.viewmodel.AuthorizationViewModel
import com.example.aneon_test_case.databinding.FragmentAuthorizationBinding
import com.example.aneon_test_case.utils.OnBackPressedListener
import com.example.aneon_test_case.utils.Status
import com.example.aneon_test_case.utils.setErrorAndColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment : Fragment(), OnBackPressedListener {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isOnline()) {
            Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_LONG).show()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.authButton.setOnClickListener {
            val login = binding.textField.editText?.text.toString()
            val password = binding.textPassword.editText?.text.toString()

            if (login.isNotBlank() && password.isNotBlank()) {
                viewModel.login(login, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Логин и пароль не могут быть пустыми",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.tokenLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    findNavController().navigate(R.id.action_authorizationFragment_to_mainFragment)
                }

                Status.ERROR -> {
                    handleLoginError(resource.message ?: "Unknown error")
                }

                Status.LOADING -> {
                    // Show loading UI if needed
                }
            }
        }
    }

    private fun handleLoginError(error: String) {
        binding.textField.setErrorAndColor(" ", Color.RED)
        binding.textPassword.setErrorAndColor(error, Color.RED)
    }
    private fun isOnline(): Boolean {
        try {
            val connectivityManager =
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val isAvailable = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

            Log.d("NetworkStatus", "isOnline: $isAvailable")
            return isAvailable
        } catch (e: Exception) {
            Log.e("NetworkStatus", "Error checking internet availability: ${e.message}")
            return false
        }
    }

    override fun onBackPressed() {

        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





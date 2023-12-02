package com.example.aneon_test_case.app.fragment

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
import com.example.aneon_test_case.utils.Status
import com.example.aneon_test_case.app.adapter.PaymentsAdapter
import com.example.aneon_test_case.app.viewmodel.MainViewModel
import com.example.aneon_test_case.databinding.FragmentMainBinding
import com.example.aneon_test_case.utils.OnBackPressedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), OnBackPressedListener {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val paymentsAdapter: PaymentsAdapter by lazy {
        PaymentsAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.recyclerViewPayments.adapter = paymentsAdapter

        binding.exit.setOnClickListener {
            mainViewModel.logout()
        }

        observeViewModel()

        mainViewModel.logoutSuccess.observe(viewLifecycleOwner) { isSuccess ->

            if (isSuccess) {
                findNavController().navigate(R.id.action_mainFragment_to_authorizationFragment)

            } else {

                Toast.makeText(requireContext(), "Что то пошло не так! проверте соединение " +
                        "с интернетом", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun observeViewModel() {
        mainViewModel.tokenLiveData.observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                mainViewModel.getPaymentsData(token)
            } else {
                Log.d("Token error", "Token is empty or not available")
            }
        }


        mainViewModel.isLoggedInLiveData.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                mainViewModel.paymentsData.observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { paymentsAdapter.setPaymentsList(it) }
                        }

                        Status.LOADING -> {
                            // Handle loading state if needed
                        }

                        Status.ERROR -> {
                            resource.message?.let {
                                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }
}








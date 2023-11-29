package com.example.aneon_test_case.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aneon_test_case.app.adapter.PaymentsAdapter
import com.example.aneon_test_case.app.viewmodel.MainViewModel
import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var paymentsAdapter: PaymentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentsAdapter = PaymentsAdapter()
        binding.recyclerViewPayments.adapter = paymentsAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            DataStoreManager.readAuthToken(requireContext()).collect { token ->
                if (!token.isNullOrBlank()) {

                    mainViewModel.getPaymentsData(token)

                    mainViewModel.paymentsData.observe(viewLifecycleOwner) { payments ->
                        payments?.let {
                            paymentsAdapter.setPaymentsList(it)
                        }
                    }

                    // Наблюдайте за ошибками в ViewModel
                    mainViewModel.error.observe(viewLifecycleOwner) { error ->
                        error?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Token is empty or not available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




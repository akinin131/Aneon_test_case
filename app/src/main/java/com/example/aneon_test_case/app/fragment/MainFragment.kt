package com.example.aneon_test_case.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.aneon_test_case.app.adapter.PaymentsAdapter
import com.example.aneon_test_case.data.network.ApiService
import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var apiService: ApiService

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

        // Инициализация RecyclerView и адаптера
        paymentsAdapter = PaymentsAdapter()
        binding.recyclerViewPayments.adapter = paymentsAdapter

        // Получение токена из DataStore
        lifecycleScope.launch(Dispatchers.Main) {
            DataStoreManager.readAuthToken(requireContext()).collect { token ->
                // Проверка, что токен не пустой, иначе запрос не выполняется
                if (!token.isNullOrBlank()) {
                    getPaymentsData(token)
                    println("MainFragment Token: $token")

                } else {
                    // Обработка ситуации, когда токен отсутствует или не удалось получить
                    Toast.makeText(requireContext(), "Token is empty or not available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getPaymentsData(token: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPayments(token)

                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    withContext(Dispatchers.Main) {
                        if (apiResponse?.success == "true") {
                            val payments = apiResponse.payments

                            println("MainFragment Response: $payments")
                            payments.let {
                                paymentsAdapter.setPaymentsList(it)
                            }

                        } else {
                            // Обработка случая, когда success не равен "true"
                            Toast.makeText(requireContext(),
                                "Error: ${apiResponse?.success}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Обработка ошибки при запросе списка платежей
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error getting payments: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Обработка общих ошибок, например, сетевых проблем
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



package com.example.aneon_test_case.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.aneon_test_case.R
import com.example.aneon_test_case.data.network.RetrofitClient
import com.example.aneon_test_case.databinding.FragmentAuthorizationBinding
import com.example.aneon_test_case.domain.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performLogin()
    }

    private fun performLogin() {
        val loginRequest = LoginRequest("demo", "12345")

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.login(loginRequest)

                withContext(Dispatchers.Main) {
                    // Извлекаем токен из ответа
                    val token = response.body()?.token


                    println("response:  $response")
                    println("response token:  $token")
                    binding.textView.text = "токен $token"
                }

            } catch (e: Exception) {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    // Отобразите сообщение об ошибке
                    println("Error token: ${e.message}")
                }
            }
        }
    }
}
package com.example.aneon_test_case.app.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aneon_test_case.R
import com.example.aneon_test_case.app.viewmodel.AuthorizationViewModel
import com.example.aneon_test_case.databinding.FragmentAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

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
       /* lifecycleScope.launch {
            DataStoreManager.readIsUserLoggedIn(requireContext()).collect { isLoggedIn ->

                if (isLoggedIn) {
                    // Пользователь авторизован, замените фрагмент на второй
                    findNavController().navigate(R.id.action_authorizationFragment_to_mainFragment)
                }

            }
        }*/
        binding.authButton.setOnClickListener {
            val login = binding.textField.editText?.text.toString()
            val password = binding.textPassword.editText?.text.toString()

            viewModel.login(login, password)



        }
        observeViewModel()

    }

    private fun observeViewModel() {
        println("Observing view model...")
        viewModel.tokenLiveData.observe(viewLifecycleOwner) { token ->

            println("Received token: $token")

            findNavController().navigate(R.id.action_authorizationFragment_to_mainFragment)

        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            // Обработка ошибки, например, отображение сообщения пользователю
            println("Error: $error")

            // Установка красного цвета EditText при ошибке
            setEditTextColors(Color.RED)

            // Установка ошибки в TextInputLayout
            binding.textField.error = error
        }
    }

    private fun resetEditTextColors() {
        setEditTextColors(Color.BLACK)

        binding.textField.error = null
    }

    private fun setEditTextColors(color: Int) {
        binding.textField.boxStrokeColor = color
        binding.textPassword.boxStrokeColor = color
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




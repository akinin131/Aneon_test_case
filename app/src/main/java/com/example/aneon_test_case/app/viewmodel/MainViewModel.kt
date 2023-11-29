package com.example.aneon_test_case.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aneon_test_case.data.models.ApiResponse
import com.example.aneon_test_case.data.models.Payment
import com.example.aneon_test_case.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _paymentsData = MutableLiveData<List<Payment>>()
    val paymentsData: LiveData<List<Payment>> get() = _paymentsData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getPaymentsData(token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPayments(token)

                withContext(Dispatchers.Main) {
                    handleResponse(response.body())
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.postValue("Error: ${e.message}")
                }
            }
        }
    }

    private fun handleResponse(apiResponse: ApiResponse?) {
        if (apiResponse?.success == "true") {
            _paymentsData.postValue(apiResponse.payments)
        } else {
            _error.postValue("Error: ${apiResponse?.success}")
        }
    }
}

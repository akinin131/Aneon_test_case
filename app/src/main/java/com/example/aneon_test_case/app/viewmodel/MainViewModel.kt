package com.example.aneon_test_case.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aneon_test_case.data.models.ApiResponse
import com.example.aneon_test_case.utils.Resource
import com.example.aneon_test_case.data.models.Payment
import com.example.aneon_test_case.data.network.ApiService
import com.example.aneon_test_case.domain.usecase.DataStoreGetUserUseCase
import com.example.aneon_test_case.domain.usecase.DataStoreLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    application: Application,
    private val dataStoreGetUserUseCase: DataStoreGetUserUseCase,
    private val dataStoreLogoutUseCase: DataStoreLogoutUseCase
) : AndroidViewModel(application) {

    private val _paymentsData = MutableLiveData<Resource<List<Payment>>>()
    val paymentsData: LiveData<Resource<List<Payment>>> get() = _paymentsData

    private val _tokenLiveData = MutableLiveData<String?>()
    val tokenLiveData: LiveData<String?> get() = _tokenLiveData


    private val _isLoggedInLiveData = MutableLiveData<Boolean>()
    val isLoggedInLiveData: LiveData<Boolean> get() = _isLoggedInLiveData

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> get() = _logoutSuccess

    init {
        observeUserDataAndPayments()
    }

    private fun observeToken() {
        viewModelScope.launch {
            dataStoreGetUserUseCase.readAuthToken().collect { token ->
                _tokenLiveData.value = token
            }
        }
    }

    private fun observeIsLoggedIn() {
        viewModelScope.launch {
            dataStoreGetUserUseCase.readIsUserLoggedIn().collect { isLoggedIn ->
                _isLoggedInLiveData.value = isLoggedIn
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutSuccess.value = try {
                dataStoreLogoutUseCase.clearUserData()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun observeUserDataAndPayments() {
        viewModelScope.launch {
            observeToken()
            observeIsLoggedIn()
        }
    }

    fun getPaymentsData(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _paymentsData.postValue(Resource.Loading())

                val response = apiService.getPayments(token)

                handleApiResponse(response)

            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private suspend fun handleApiResponse(response: Response<ApiResponse>) {
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                handleSuccessfulResponse(response.body())
            } else {
                handleErrorResponse(response.code())
            }
        }
    }

    private fun handleSuccessfulResponse(apiResponse: ApiResponse?) {
        if (apiResponse?.success == "true") {
            val payments = apiResponse.payments
            _paymentsData.postValue(Resource.Success(payments))
        } else {
            val errorMessage = "Error: ${apiResponse?.success}"
            _paymentsData.postValue(Resource.Error(errorMessage))
        }
    }

    private fun handleErrorResponse(errorCode: Int) {
        val errorMessage = "Error getting payments: $errorCode"
        _paymentsData.postValue(Resource.Error(errorMessage))
    }

    private fun handleException(exception: Exception) {
        val errorMessage = "Error: ${exception.message}"
        _paymentsData.postValue(Resource.Error(errorMessage))
    }

}




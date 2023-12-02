package com.example.aneon_test_case.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aneon_test_case.data.models.LoginRequest
import com.example.aneon_test_case.data.network.ApiService
import com.example.aneon_test_case.domain.usecase.DataStoreSaveUseCase
import com.example.aneon_test_case.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    application: Application,
    private val apiService: ApiService,
    private val dataStoreSaveUseCase: DataStoreSaveUseCase,
) : AndroidViewModel(application) {

    private val _tokenLiveData = MutableLiveData<Resource<String?>>()
    val tokenLiveData: LiveData<Resource<String?>> get() = _tokenLiveData

    fun login(login: String, password: String) {
        val loginRequest = LoginRequest(login, password)

        viewModelScope.launch {
            try {
                _tokenLiveData.value = Resource.Loading()

                val response = apiService.login(loginRequest)

                if (response.isSuccessful) {
                    handleSuccessfulResponse(response)
                } else {
                    handleErrorResponse(response)
                }
            } catch (e: Exception) {
                _tokenLiveData.value = Resource.Error("Неверный логин или пароль")
            }
        }
    }

    private suspend fun handleSuccessfulResponse(response: Response<ResponseBody>) {
        val responseBody = response.body()?.string()
        val jsonObject = responseBody?.let { JSONObject(it) }
        val responseObj = jsonObject?.getJSONObject("response")
        val token = responseObj?.getString("token")

        if (!token.isNullOrBlank()) {
            saveTokenAndLoggedInState(token)
            _tokenLiveData.value = Resource.Success(token)
        }
    }

    private fun handleErrorResponse(response: Response<ResponseBody>) {
        _tokenLiveData.value = Resource.Error("Error: ${response.code()}")
    }

    private suspend fun saveTokenAndLoggedInState(token: String) {
        viewModelScope.launch {
            dataStoreSaveUseCase.saveAuthToken(token)
            dataStoreSaveUseCase.saveIsUserLoggedIn(true)
        }
    }
}





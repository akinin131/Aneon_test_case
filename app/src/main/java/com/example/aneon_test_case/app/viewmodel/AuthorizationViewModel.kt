package com.example.aneon_test_case.app.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.data.models.LoginRequest
import com.example.aneon_test_case.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: ApiService
) : ViewModel() {

    private val _tokenLiveData = MutableLiveData<String?>()
    val tokenLiveData: MutableLiveData<String?> get() = _tokenLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun login(login: String, password: String) {
        val loginRequest = LoginRequest(login, password)

        viewModelScope.launch {
            try {
                val response = apiService.login(loginRequest)

                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val jsonObject = responseBody?.let { JSONObject(it) }
                    val responseObj = jsonObject?.getJSONObject("response")
                    val token = responseObj?.getString("token")

                    _tokenLiveData.value = token

                    if (!token.isNullOrBlank()) {

                        DataStoreManager.saveAuthToken(context, token)
                        DataStoreManager.saveIsUserLoggedIn(context, true)
                    }
                } else {

                    println("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                    println("Error token: ${e.message}")

                }
            }
        }
    }
}


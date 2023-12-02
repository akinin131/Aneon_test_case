package com.example.aneon_test_case.app.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aneon_test_case.utils.Event
import com.example.aneon_test_case.R
import com.example.aneon_test_case.domain.usecase.DataStoreGetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataStoreGetUserUseCase: DataStoreGetUserUseCase
) : ViewModel() {

    private val _navigationEvent = MutableLiveData<Event<Int>>()
    val navigationEvent: LiveData<Event<Int>> get() = _navigationEvent

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            dataStoreGetUserUseCase.readIsUserLoggedIn().collect { isLoggedIn ->
                val destination = if (isLoggedIn) R.id.action_start_to_mainFragment
                else R.id.action_start_to_authorizationFragment

                _navigationEvent.value = Event(destination)
            }
        }
    }
}


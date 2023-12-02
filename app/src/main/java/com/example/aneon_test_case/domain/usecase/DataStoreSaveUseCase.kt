package com.example.aneon_test_case.domain.usecase

import com.example.aneon_test_case.domain.repository.DataStoreRepositorySave
import javax.inject.Inject

class DataStoreSaveUseCase @Inject constructor(private val saveRepository: DataStoreRepositorySave) {
    suspend fun saveAuthToken(token: String) {
        saveRepository.saveAuthToken(token)
    }

    suspend fun saveIsUserLoggedIn(isLoggedIn: Boolean) {
        saveRepository.saveIsUserLoggedIn(isLoggedIn)
    }
}




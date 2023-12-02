package com.example.aneon_test_case.domain.usecase

import com.example.aneon_test_case.domain.repository.DataStoreRepositoryGetUser
import kotlinx.coroutines.flow.Flow

class DataStoreGetUserUseCase(private val getUserRepository: DataStoreRepositoryGetUser) {
    fun readAuthToken(): Flow<String?> {
        return getUserRepository.readAuthToken()
    }

    fun readIsUserLoggedIn(): Flow<Boolean> {
        return getUserRepository.readIsUserLoggedIn()
    }
}

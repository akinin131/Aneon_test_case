package com.example.aneon_test_case.domain.usecase

import com.example.aneon_test_case.domain.repository.DataStoreRepositoryLogout

class DataStoreLogoutUseCase(private val logoutRepository: DataStoreRepositoryLogout) {
    suspend fun clearUserData() {
        return logoutRepository.clearUserData()
    }
}

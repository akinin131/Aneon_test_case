package com.example.aneon_test_case.data.repositoryImpl

import android.content.Context
import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.domain.repository.DataStoreRepositoryLogout

class DataStoreLogoutRepository(private val context: Context) : DataStoreRepositoryLogout {
    override suspend fun clearUserData() {
        DataStoreManager.clearUserData(context)
    }
}


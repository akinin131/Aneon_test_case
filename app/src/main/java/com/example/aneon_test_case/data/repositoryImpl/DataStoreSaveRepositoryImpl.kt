package com.example.aneon_test_case.data.repositoryImpl

import android.content.Context

import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.domain.repository.DataStoreRepositorySave

class DataStoreSaveRepository(private val context: Context) : DataStoreRepositorySave {
    override suspend fun saveAuthToken(token: String) {
        DataStoreManager.saveAuthToken(context, token)
    }

    override suspend fun saveIsUserLoggedIn(isLoggedIn: Boolean) {
        DataStoreManager.saveIsUserLoggedIn(context, isLoggedIn)
    }
}
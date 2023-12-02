package com.example.aneon_test_case.data.repositoryImpl

import android.content.Context
import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.domain.repository.DataStoreRepositoryGetUser
import kotlinx.coroutines.flow.Flow

class DataStoreGetUserRepository(private val context: Context) : DataStoreRepositoryGetUser {

    override fun readAuthToken(): Flow<String?> {
        return DataStoreManager.readAuthToken(context)
    }

    override fun readIsUserLoggedIn(): Flow<Boolean> {
        return DataStoreManager.readIsUserLoggedIn(context)
    }
}

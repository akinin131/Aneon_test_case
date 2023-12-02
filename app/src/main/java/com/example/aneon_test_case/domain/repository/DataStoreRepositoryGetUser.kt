package com.example.aneon_test_case.domain.repository

import kotlinx.coroutines.flow.Flow
interface DataStoreRepositoryGetUser {
    fun readAuthToken(): Flow<String?>
    fun readIsUserLoggedIn(): Flow<Boolean>

}
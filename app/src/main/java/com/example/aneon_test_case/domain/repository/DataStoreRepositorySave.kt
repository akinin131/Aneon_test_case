package com.example.aneon_test_case.domain.repository

interface DataStoreRepositorySave {
    suspend fun saveAuthToken(token: String)
    suspend fun saveIsUserLoggedIn(isLoggedIn: Boolean)

}
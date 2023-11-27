package com.example.aneon_test_case.data.network

import com.example.aneon_test_case.domain.models.LoginRequest
import com.example.aneon_test_case.domain.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
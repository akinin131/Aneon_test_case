package com.example.aneon_test_case.data.network

import com.example.aneon_test_case.data.models.ApiResponse
import com.example.aneon_test_case.data.models.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseBody>

    @Headers("app-key: 12345", "v: 1")
    @GET("payments")
    suspend fun getPayments(@Header("token") token: String): Response<ApiResponse>

}

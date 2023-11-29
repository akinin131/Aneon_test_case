package com.example.aneon_test_case.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("success") val success: String,
    @SerializedName("response") val payments: List<Payment>
)
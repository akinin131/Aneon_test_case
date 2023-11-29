package com.example.aneon_test_case.app.viewmodel

import com.example.aneon_test_case.data.models.Payment

data class ApiResponseSuccess(
    val success: String?,
    val payments: List<Payment>?
)

package com.example.aneon_test_case.domain.models

data class LoginRequest(val login: String, val password: String)
data class LoginResponse(val token: String)

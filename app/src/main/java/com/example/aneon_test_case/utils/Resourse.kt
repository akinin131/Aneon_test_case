package com.example.aneon_test_case.utils

sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(Status.SUCCESS, data)
    class Loading<T>(data: T? = null) : Resource<T>(Status.LOADING, data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(Status.ERROR, data, message)
}



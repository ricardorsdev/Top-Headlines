package com.example.topheadlines.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T>: NetworkResult<T>()
    class Success<T>(data: T?): NetworkResult<T>(data = data)
    class Failure<T>(message: String?): NetworkResult<T>(message = message)
}
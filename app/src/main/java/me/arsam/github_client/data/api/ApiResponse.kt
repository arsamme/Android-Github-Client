package me.arsam.github_client.data.api

import com.apollographql.apollo.api.Response

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(data: T?): ApiResponse<T> {
            return if (data != null) {
                ApiSuccessResponse(data)
            } else {
                ApiEmptyResponse()
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()
data class ApiSuccessResponse<T>(val data: T) : ApiResponse<T>()
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
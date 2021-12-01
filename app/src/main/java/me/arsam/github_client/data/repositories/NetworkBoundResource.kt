package me.arsam.github_client.data.repositories


import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.arsam.github_client.data.Resource
import me.arsam.github_client.data.api.ApiEmptyResponse
import me.arsam.github_client.data.api.ApiErrorResponse
import me.arsam.github_client.data.api.ApiResponse
import me.arsam.github_client.data.api.ApiSuccessResponse

@MainThread
abstract class NetworkBoundResource<ResultType> {
    fun asFlow(): Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading)
        val dbValue = loadFromDb()
        if (shouldFetch(dbValue)) {
            when (val apiResponse = fetchFromNetwork()) {
                is ApiSuccessResponse -> {
                    saveNetworkResult(processResponse(apiResponse))
                    emit(Resource.Success(apiResponse.data))
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiEmptyResponse -> {
                    emit(Resource.Empty)
                }
            }
        } else {
            if (dbValue != null) {
                emit(Resource.Success(dbValue))
            } else {
                emit(Resource.Empty)
            }
        }
    }

    protected open fun onFetchFailed() {
        // Implement in sub-classes to handle errors
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<ResultType>) = response.data

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType?

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): ApiResponse<ResultType>
}
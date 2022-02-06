package com.spidugu.nycshools.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

/**
 * Mapper Function to take in the dispatcher and perform the network related operation
 * and carry out the result interms of ResponseResult
 * @param dispatcher decides the worker thread
 * @param apiCall invoking API request.
 * @return ResponseResult<*> i.e either Success/Failure/Network.
 */

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResponseResult<T> {
    return withContext(dispatcher) {
        try {
            ResponseResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResponseResult.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    ResponseResult.GenericError(code, throwable)
                }
                else -> {
                    ResponseResult.GenericError(null, null)
                }
            }
        }
    }
}
package com.spidugu.nycshools.util

/**
 * A sealed class allows you to represent constrained hierarchies in which an object can only be of one of the given types.
 */
sealed class ResponseResult<out T> {
    data class Success<out T>(val value: T) : ResponseResult<T>()
    data class GenericError(val code: Int? = null, val error: Throwable? = null) :
        ResponseResult<Nothing>()

    object NetworkError : ResponseResult<Nothing>()
}


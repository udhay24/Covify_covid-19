package com.udhay.helfy.util

import java.lang.Exception

sealed class Resource<out T, out S> {

    data class SuccessResponse<T>(val body: T): Resource<T, Nothing>()

    data class FailureResponse(val error: Exception): Resource<Nothing, Exception>()

    object LoadingResponse : Resource<Nothing, Nothing>()
}
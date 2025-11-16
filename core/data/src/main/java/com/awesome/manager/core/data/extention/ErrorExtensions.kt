package com.awesome.manager.core.data.extention

import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.network.NetworkError

fun Throwable.asAmError(): ProcessStates.Error = ProcessStates.Error(
    when (this) {
        is NetworkError -> when (this) {
            is NetworkError.ConnectionError, is NetworkError.ConvertDataError,
            is NetworkError.InternalServerError, is NetworkError.TooManyRequests ->
                AmUIError.ConnectionUIError

            is NetworkError.Unauthorized, is NetworkError.Forbidden ->
                AmUIError.Unauthorized

            is NetworkError.RequestTimeout ->
                AmUIError.PoorConnection

            is NetworkError.BadRequest ->
                AmUIError.BadRequest(errorMessage)

            is NetworkError.OtherError ->
                AmUIError.OtherUIError(errorMessage)
        }

        else -> AmUIError.UnknownUIError
    }
)
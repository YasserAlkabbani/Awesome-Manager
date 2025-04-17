package com.awesome.manager.core.common

sealed interface AmUIError {
    data object NoError : AmUIError
    data object ConnectionUIError : AmUIError
    data object Unauthorized : AmUIError
    data object PoorConnection : AmUIError
    data object UnknownUIError : AmUIError
    data object NoDataError : AmUIError
    data object NoPermissionError : AmUIError
    data class BadRequest(val errorMessage: String) : AmUIError
    data class OtherUIError(val errorMessage: String) : AmUIError
}

sealed interface AmState<out T> {
    data class Success<T>(val data: T) : AmState<T>
    data class Error(val amUIError: AmUIError) : AmState<Nothing>
    data class Loading(val progress: Int = 0) : AmState<Nothing>

    fun isSuccess() = this is Success
    fun isError() = this is Error
    fun isLoading() = this is Loading

}
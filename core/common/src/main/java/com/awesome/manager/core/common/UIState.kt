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

sealed interface ProcessStates<out T> {
    data class Success<T>(val data: T) : ProcessStates<T>
    data class Error(val amUIError: AmUIError) : ProcessStates<Nothing>
    data class Loading(val progress: Int = 0) : ProcessStates<Nothing>

    fun isSuccess() = this is Success
    fun isError() = this is Error
    fun isLoading() = this is Loading

    fun asUIState() = when (this) {
        is Error -> UIStates.Error(amUIError)
        is Loading -> UIStates.Loading
        is Success<*> -> UIStates.Success
    }

}

sealed interface UIStates {
    data object Success : UIStates
    data class Error(val amUIError: AmUIError) : UIStates
    data object Loading : UIStates
}


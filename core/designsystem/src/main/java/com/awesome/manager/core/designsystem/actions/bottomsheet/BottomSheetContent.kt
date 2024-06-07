package com.awesome.manager.core.designsystem.actions.bottomsheet

import androidx.compose.foundation.lazy.LazyListScope

sealed class BottomSheetContent {

    data class Profile(
        val email: String,
        val logout: () -> Unit
    ) : BottomSheetContent()

    data class SearchForAccount(
        val items: LazyListScope.() -> Unit,
        val onReSearch: (String) -> Unit
    ) : BottomSheetContent()

    data class AccountCreated(val dismiss: () -> Unit) :
        BottomSheetContent()

    data class PasswordRested(val dismiss: () -> Unit) :
        BottomSheetContent()

    data class AuthError(
        val errorMessage: String, val createNewAccount: () -> Unit, val editCredentials: () -> Unit
    ) : BottomSheetContent()

    data class UnknownError(val dismiss: () -> Unit) :
        BottomSheetContent()

    data class ConnectionError(val dismiss: () -> Unit) :
        BottomSheetContent()

    data class CustomError(
        val errorMessage: String, val dismiss: () -> Unit
    ) : BottomSheetContent()

    data class PickDate(
        val initTime: Long, val setDate: (Long) -> Unit, val dismiss: () -> Unit
    ) : BottomSheetContent()

    data class PickRangeDate(
        val initTime: Long, val setDate: (Long, Long) -> Unit, val dismiss: () -> Unit
    ) : BottomSheetContent()

}
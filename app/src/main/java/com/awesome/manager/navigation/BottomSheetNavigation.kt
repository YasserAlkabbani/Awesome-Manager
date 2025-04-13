package com.awesome.manager.navigation

import com.awesome.manager.core.common.AmUIError
import kotlinx.serialization.Serializable

sealed interface BottomSheetNavigation {
    @Serializable
    data object ConnectionError : BottomSheetNavigation
    @Serializable
    data object UnauthorizedError : BottomSheetNavigation
    @Serializable
    data object PoorConnectionError : BottomSheetNavigation
    @Serializable
    data object UnknownError : BottomSheetNavigation
    @Serializable
    data object NoDataError : BottomSheetNavigation
    @Serializable
    data object NoPermissionError : BottomSheetNavigation
    @Serializable
    data object BadRequestError : BottomSheetNavigation
    @Serializable
    data object OtherUIError : BottomSheetNavigation
}

fun AmUIError.getBottomSheetNavigation() = when (this) {
    AmUIError.ConnectionUIError -> BottomSheetNavigation.ConnectionError
    AmUIError.NoDataError -> BottomSheetNavigation.NoDataError
    AmUIError.NoPermissionError -> BottomSheetNavigation.NoPermissionError
    AmUIError.PoorConnection -> BottomSheetNavigation.PoorConnectionError
    AmUIError.Unauthorized -> BottomSheetNavigation.UnauthorizedError
    AmUIError.UnknownUIError -> BottomSheetNavigation.UnknownError
    is AmUIError.BadRequest -> BottomSheetNavigation.BadRequestError
    is AmUIError.OtherUIError -> BottomSheetNavigation.OtherUIError
    AmUIError.NoError -> null
}
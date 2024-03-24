package com.awesome.manager.core.ui.bottom_sheets.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.ui_actions.BottomSheetAction
import com.awesome.manager.core.ui.R
import com.awesome.manager.core.ui.bottom_sheets.AmBottomSheetMessage
import com.awesome.manager.core.ui.bottom_sheets.MessageBottomData

@Composable
fun BottomSheetUnknownError(
    unknownError: BottomSheetAction.UnknownError
) {
    AmBottomSheetMessage(
        title = stringResource(R.string.there_is_something_wrong),
        subtitle = stringResource(R.string.there_is_unknown_error_try_again_later),
        positive = false,
        button1 = MessageBottomData(text = "Ok", positive = true, onClick = unknownError.dismiss),
        button2 = null, button3 = null,
    )
}

@Composable
fun BottomSheetCustomError(
    unknownError: BottomSheetAction.CustomError
) {
    AmBottomSheetMessage(
        title = stringResource(R.string.there_is_something_wrong),
        subtitle = stringResource(R.string.there_is_unknown_error_try_again_later),
        positive = false,
        button1 = MessageBottomData(text = "Ok", positive = true, onClick = unknownError.dismiss),
        button2 = null, button3 = null,
    )
}

@Composable
fun BottomSheetConnectionError(
    unknownError: BottomSheetAction.ConnectionError
) {
    AmBottomSheetMessage(
        title = stringResource(R.string.there_is_something_wrong),
        subtitle = stringResource(R.string.there_is_unknown_error_try_again_later),
        positive = false,
        button1 = MessageBottomData(text = "Ok", positive = true, onClick = unknownError.dismiss),
        button2 = null, button3 = null,
    )
}
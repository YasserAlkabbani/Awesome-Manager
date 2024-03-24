package com.awesome.manager.core.ui.bottom_sheets.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.ui_actions.BottomSheetAction
import com.awesome.manager.core.ui.R
import com.awesome.manager.core.ui.bottom_sheets.AmBottomSheetMessage
import com.awesome.manager.core.ui.bottom_sheets.MessageBottomData

@Composable
fun BottomSheetAuthError(
    bottomSheetAction: BottomSheetAction.AuthError
) {
    AmBottomSheetMessage(
        title = "ERROR TITLE",
        subtitle = "ERROR SUBTITLE",
        positive = null,
        button1 = MessageBottomData(
            text = stringResource(R.string.create_a_new_account),
            positive = true,
            onClick = {}
        ),
        button2 = MessageBottomData(
            text = stringResource(R.string.edit_credentials),
            positive = true,
            onClick = bottomSheetAction.dismiss
        ),
        button3 = null, /*MessageBottomData(text="Reset Password", positive = false, onClick = authScreenState.resetPassword)*/
    )
}
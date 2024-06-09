package com.awesome.manager.core.ui.bottom_sheets.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.actions.main.BottomSheetAction
import com.awesome.manager.core.ui.R
import com.awesome.manager.core.ui.bottom_sheets.AmBottomSheetMessage
import com.awesome.manager.core.ui.bottom_sheets.MessageBottomData

@Composable
fun BottomSheetAuthError(
    bottomSheetAction: BottomSheetContent.AuthError
) {
    AmBottomSheetMessage(
        title = stringResource(R.string.something_wrong),
        subtitle = bottomSheetAction.errorMessage,
        positive = false,
        button1 = MessageBottomData(
            text = stringResource(R.string.create_and_confirm_account),
            positive = true,
            onClick = bottomSheetAction.createNewAccount
        ),
        button2 = MessageBottomData(
            text = stringResource(R.string.edit_credentials),
            positive = true,
            onClick = bottomSheetAction.editCredentials
        ),
        button3 = null,
    )
}


@Preview
@Composable
fun BottomSheetAuthErrorPreview() {
    BottomSheetAuthError(
        BottomSheetContent.AuthError("", {}, {})
    )
}
package com.awesome.manager.core.ui.bottom_sheets.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.ui_actions.BottomSheetAction
import com.awesome.manager.core.ui.R
import com.awesome.manager.core.ui.bottom_sheets.AmBottomSheetMessage
import com.awesome.manager.core.ui.bottom_sheets.MessageBottomData

@Composable
fun BottomSheetPasswordRestered(
    passwordRested: BottomSheetAction.PasswordRested
) {
    AmBottomSheetMessage(
        title = stringResource(R.string.reset_password_link_has_sent_successfully),
        subtitle = stringResource(R.string.we_sent_you_a_reset_password_link_please_check_your_email_to_create_your_new_password),
        positive = false,
        button1 = MessageBottomData(text= stringResource(R.string.check_email), positive = true, onClick = passwordRested.dismiss),
        button2 = null, button3 = null,
    )
}
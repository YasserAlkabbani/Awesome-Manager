package com.awesome.manager.core.ui.bottom_sheets.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.ui_actions.BottomSheetAction
import com.awesome.manager.core.ui.R
import com.awesome.manager.core.ui.bottom_sheets.AmBottomSheetMessage
import com.awesome.manager.core.ui.bottom_sheets.MessageBottomData

@Composable
fun BottomSheetAccountCreated(
    accountCreated: BottomSheetAction.AccountCreated
) {
    AmBottomSheetMessage(
        title = stringResource(R.string.your_account_has_created_successfully),
        subtitle = stringResource(R.string.we_sent_you_a_confirmation_link_please_check_your_email),
        positive = true,
        button1 = MessageBottomData(text = "OK", positive = true, onClick = accountCreated.dismiss),
        button2 = null, button3 = null,
    )
}
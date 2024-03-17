package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmTextWithLabel
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.ui.R

@Composable
fun BottomSheetProfile(email: String, logout: () -> Unit) {
    AmCard(modifier = Modifier.padding(vertical = 5.dp), positive = false) {
        AmTextWithLabel(label = stringResource(R.string.email), text = email, positive = false)
        Spacer(modifier = Modifier.height(30.dp))
        AmButton(
            modifier = Modifier.fillMaxWidth(),
            text = "LOGOUT", positive = false,
            onClick = logout
        )
    }
}


@Preview
@Composable
fun BottomSheetProfilePreview() {
    BottomSheetProfile("YASSER@GMAIL.COM", {})
}
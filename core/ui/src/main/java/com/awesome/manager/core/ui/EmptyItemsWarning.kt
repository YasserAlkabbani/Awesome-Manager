package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun NoDataWarning(
    title: String,
    buttonText: String,
    onClickButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(AmPadding.XX_LARGE.value)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AmText(
            text = title,
            maxLines = 3,
            textAlign = TextAlign.Center
        )
        AmFilledTonalButton(
            text = buttonText,
            onClick = onClickButton,
        )
    }
}
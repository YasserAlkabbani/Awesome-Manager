package com.awesome.manager.core.designsystem.component.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.awesome.manager.core.designsystem.AmPadding

@Composable
fun AmText(
    modifier: Modifier = Modifier,
    text: String, maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier.padding(horizontal = AmPadding.LARGE.value),
        text = text,
        maxLines = maxLines,
        style = style,
        textAlign = textAlign,
    )
}
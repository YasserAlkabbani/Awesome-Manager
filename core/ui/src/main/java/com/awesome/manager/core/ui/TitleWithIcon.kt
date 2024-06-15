package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerMediumWidth
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallWidth
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmTextWithIcon(
    modifier: Modifier = Modifier,
    text: String, textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    amIconsType: AmIconsType,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AmIcon(
            modifier = Modifier.size(AmSize.X_SMALL.value),
            amIconsType = amIconsType
        )
        AmSpacerMediumWidth()
        AmText(text = text, style = textStyle)
    }
}

@Preview
@Composable
fun AmTitleWithIconPreview() {
    Surface {
        AmTextWithIcon(
            text = "TITLE",
            amIconsType = AmIcons.AwesomeManagerIcon,
        )
    }
}
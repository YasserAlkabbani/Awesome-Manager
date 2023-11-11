package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmTextWithIcon(
    modifier: Modifier = Modifier,
    text: String, amIconsType: AmIconsType, positive: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AmSurface(positive = positive, highPadding = false) {
                AmIcon(
                    Modifier.size(14.dp), amIconsType = amIconsType,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            AmText(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun AmTitleWithSubtitlePreview() {
    Surface {
        AmTextWithIcon(
            text = "TITLE",
            amIconsType = AmIcons.AwesomeManagerIcon,
            positive = true
        )
    }
}
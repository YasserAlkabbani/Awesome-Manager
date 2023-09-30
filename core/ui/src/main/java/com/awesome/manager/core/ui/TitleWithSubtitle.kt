package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun AmTitleWithSubtitle(
    modifier: Modifier = Modifier, title: String, subtitle: String,
    amIconsType: AmIconsType, positive: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AmText(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(8.dp))
            AmSurface(positive = positive) {
                AmIcon(Modifier.size(14.dp), amIconsType = amIconsType)
            }
        }
        AmText(text = subtitle, style = MaterialTheme.typography.titleSmall)
    }
}

@Preview
@Composable
fun AmTitleWithSubtitlePreview() {
    Surface {
        AmTitleWithSubtitle(
            title = "TITLE",
            subtitle = "SUBTITLE",
            amIconsType = AmIcons.AwesomeManagerIcon,
            positive = true
        )
    }
}
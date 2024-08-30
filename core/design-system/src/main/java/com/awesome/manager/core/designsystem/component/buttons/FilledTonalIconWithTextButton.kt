package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmCircularProgress
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerMediumWidth
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmFilledTonalIconWithTextButton(
    modifier: Modifier = Modifier,
    text: String, amIconsType: AmIconsType,
    positive: Boolean?, onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AmCard(
            modifier = Modifier, onClick = onClick, positive = positive,
            padding = AmPadding.LARGE
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmIcon(modifier = Modifier, amIconsType = amIconsType)
                AmSpacerMediumWidth()
                AmText(text = text, style = MaterialTheme.typography.titleMedium)
            }
        }
    }

}


@Preview
@Composable
fun AmFilledTonalIconButtonWithPositivePreview() {
    AmFilledTonalIconWithTextButton(
        modifier = Modifier,
        text = "TEST TEXT", amIconsType = AmIcons.Save,
        positive = true, onClick = {}
    )
}

@Preview
@Composable
fun AmFilledTonalIconButtonWithNegativePreview() {
    AmFilledTonalIconWithTextButton(
        modifier = Modifier,
        text = "TEST TEXT", amIconsType = AmIcons.Save,
        positive = false, onClick = {}
    )
}
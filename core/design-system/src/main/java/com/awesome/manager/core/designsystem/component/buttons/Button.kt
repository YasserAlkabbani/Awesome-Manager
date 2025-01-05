package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerLargeWidth
import com.awesome.manager.core.designsystem.component.cards.AmCard
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmButton(
    modifier: Modifier = Modifier,
    text: String,
    isPositive: Boolean?,
    onClick: () -> Unit,
    amIconsType: AmIconsType.ImageVictorAmIconsType?
) {
    AmCard(
        modifier = modifier,
        padding = AmPadding.BUTTON,
        isPositive = isPositive,
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmText(
                    text = text.uppercase(),
                    style = MaterialTheme.typography.titleSmall
                )
                AmSpacerLargeWidth()
                amIconsType?.let { AmIcon(amIconsType = amIconsType) }
            }
        }
    )
}

@Preview
@Composable
fun AmButtonPreview() {
    AmButton(
        text = "CLICK ME !!",
        isPositive = true,
        onClick = {},
        amIconsType = AmIcons.Save
    )
}
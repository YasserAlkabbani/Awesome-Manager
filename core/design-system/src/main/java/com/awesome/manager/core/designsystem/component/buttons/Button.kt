package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerLargeHeight
import com.awesome.manager.core.designsystem.component.AmSpacerLargeWidth
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    amIconsType: AmIconsType.ImageVictorAmIconsType?
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(
            vertical = AmPadding.LARGE.value,
            horizontal = AmPadding.LARGE.value
        ),
    ) {
        AmText(
            text = text.uppercase(),
        )
        AmSpacerLargeWidth()
        amIconsType?.let { AmIcon(amIconsType = amIconsType) }
    }
}

@Preview
@Composable
fun AmButtonPreview() {
    AmButton(
        text = "CLICK ME !!",
        onClick = {},
        amIconsType = AmIcons.Save
    )
}
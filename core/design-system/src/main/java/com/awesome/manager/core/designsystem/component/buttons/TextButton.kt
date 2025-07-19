package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4_XL
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9_PRO_XL
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerWidth
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    amIconsType: AmIconsType.ImageVictorAmIconsType?,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
//        colors = ButtonDefaults.textButtonColors().run {
//            copy(contentColor = MaterialTheme.colorScheme.error)
//        },
        content = {
            AmText(
                modifier = Modifier.align(Alignment.CenterVertically),
                textStyle = MaterialTheme.typography.titleMedium,
                text = text.uppercase()
            )
            amIconsType?.let {
                AmSpacerWidth(AmPadding.SMALL)
                AmIcon(
                    modifier = Modifier,
                    amIconsType = amIconsType,
                )
            }
        },
        enabled = enabled,
        onClick = onClick
    )
}

@Preview(device = PIXEL_9_PRO_XL, showBackground = true)
@Composable
fun AmTextButtonPreview() {
    AmTextButton(
        text = "CLICK ME !!",
        enabled = false,
        onClick = {},
        amIconsType = AmIcons.Save
    )
}
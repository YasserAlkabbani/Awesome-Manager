package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
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
fun AmButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    amIconsType: AmIconsType.ImageVictorAmIconsType?
) {
    Button(
        modifier = modifier,
        enabled = enabled,
//        colors = ButtonDefaults.buttonColors().run {
//            when (isError) {
//                true -> copy(containerColor = MaterialTheme.colorScheme.error)
//                false -> this
//            }
//        },
        content = {
            AmText(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text.uppercase(),
                textStyle = MaterialTheme.typography.titleMedium
            )
            amIconsType?.let {
                AmSpacerWidth(AmPadding.SMALL)
                AmIcon(
                    modifier = Modifier,
                    amIconsType = amIconsType,
                )
            }
        },
        onClick = onClick
    )
}

@Preview(device = PIXEL_9_PRO_XL, showBackground = true)
@Composable
fun AmButtonPreview() {
    AmButton(
        text = "CLICK ME !!",
        enabled = false,
        onClick = {},
        amIconsType = AmIcons.Save
    )
}
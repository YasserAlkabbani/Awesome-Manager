package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType

data class FTBButtonData(
    val text: String = "",
    val amIconsType: AmIconsType.ImageVictorAmIconsType,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.AMHorizontalFloatingToolbar(
    isError: Boolean,
    text: String,
    mainButton: FTBButtonData?,
    validationButton: FTBButtonData?
) {
    HorizontalFloatingToolbar(
        modifier = Modifier.align(Alignment.BottomCenter),
        expanded = !isError,
//            contentPadding = PaddingValues(0.dp),
        leadingContent = {

        },
        trailingContent = {
            validationButton?.let {
                AmButton(
                    text = validationButton.text,
                    amIconsType = validationButton.amIconsType,
                    onClick = validationButton.onClick,
                    isError = isError
                )
            }
        },
        colors = when (isError) {
            false -> FloatingToolbarDefaults.vibrantFloatingToolbarColors()
            true -> FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                toolbarContainerColor = MaterialTheme.colorScheme.errorContainer
            )
        },
        content = {
            mainButton?.let {
                AmIconButton(
                    modifier = Modifier,
                    amIconsType = mainButton.amIconsType,
                    onClick = mainButton.onClick,
                )
            }
            AmText(
                modifier = Modifier.align(Alignment.CenterVertically),
                textStyle = MaterialTheme.typography.labelLarge,
                text = text
            )
            AmSpacerMediumWidth()
        },
    )
}
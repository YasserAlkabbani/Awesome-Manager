package com.awesome.manager.core.designsystem.component.chips

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons

enum class AmChipPosation{
    FIRST,MID,LAST,
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AmChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    title: String,
    amChipPosition: AmChipPosation,
    onClick: () -> Unit,
) {
    ToggleButton(
        checked = isSelected,
        onCheckedChange = { onClick() },
        shapes =
            when (amChipPosition) {
                AmChipPosation.FIRST -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                AmChipPosation.MID -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                AmChipPosation.LAST -> ButtonGroupDefaults.connectedTrailingButtonShapes()
            },
        modifier = Modifier.semantics { role = Role.RadioButton },
    ) {
        AmIcon(
            amIconsType = when(isSelected){
                true -> AmIcons.Selected
                false -> AmIcons.NotSelected
            },
        )
        Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
        AmText(text = title)
    }
}

@Preview
@Composable
fun AmChipSelectedPreview() {
    AmChip(
        isSelected = true,
        title = "TEST",
        amChipPosition = AmChipPosation.MID,
        onClick = {}
    )
}

@Preview
@Composable
fun AmChipUnSelectedPreview() {
    AmChip(
        isSelected = false,
        title = "TEST",
        amChipPosition = AmChipPosation.MID,
        onClick = {}
    )
}
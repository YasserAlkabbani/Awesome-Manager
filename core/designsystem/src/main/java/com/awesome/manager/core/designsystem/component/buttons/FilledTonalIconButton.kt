package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmFilledTonalIconButton(
    modifier: Modifier=Modifier,
    amIconsType: AmIconsType,
    isPositive:Boolean,
    onClick:()->Unit
){
    val containerColor=
        if (isPositive)MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
    FilledTonalButton(
        modifier=modifier,
        colors = ButtonDefaults.filledTonalButtonColors(containerColor = containerColor),
        onClick = onClick
    ) {
        AmIcon(amIconsType = amIconsType)
    }
}


@Preview
@Composable
fun AmFilledTonalIconButtonPositivePreview(){
    AmFilledTonalIconButton(
        modifier = Modifier,
        amIconsType = AmIcons.Save,
        isPositive = true,
        onClick = {}
    )
}
@Preview
@Composable
fun AmFilledTonalIconButtonNegativePreview(){
    AmFilledTonalIconButton(
        modifier = Modifier,
        amIconsType = AmIcons.Save,
        isPositive = false,
        onClick = {}
    )
}
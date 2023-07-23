package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
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
    AmCard(
        modifier=modifier,
        onClick = onClick,
        positive = isPositive,
        loading = false
    ) {
        AmIcon(modifier=Modifier.padding(horizontal = 4.dp),amIconsType = amIconsType)
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
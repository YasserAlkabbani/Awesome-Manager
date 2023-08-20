package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmFilledTonalIconWithTextButton(
    modifier: Modifier=Modifier,
    text:String,amIconsType: AmIconsType,
    isPositive:Boolean,
    onClick:()->Unit
){
    AmSurface(
        modifier=modifier,
        onClick = onClick,
        balance = isPositive,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AmText(text = text, style = MaterialTheme.typography.titleLarge)
            AmCard(balance = isPositive) {
                AmIcon(modifier=Modifier.size(32.dp),amIconsType = amIconsType)
            }
        }
    }
}


@Preview
@Composable
fun AmFilledTonalIconButtonWithPositivePreview(){
    AmFilledTonalIconWithTextButton(
        modifier = Modifier,
        text = "TEST TEXT",amIconsType = AmIcons.Save,
        isPositive = true,
        onClick = {}
    )
}
@Preview
@Composable
fun AmFilledTonalIconButtonWithNegativePreview(){
    AmFilledTonalIconWithTextButton(
        modifier = Modifier,
        text = "TEST TEXT",amIconsType = AmIcons.Save,
        isPositive = false,
        onClick = {}
    )
}
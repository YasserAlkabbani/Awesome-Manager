package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.icon.AmIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmChip(
    modifier: Modifier=Modifier,
    selected:Boolean,
    label:String,
    onClick:()->Unit,
){
    val shape=if (selected) MaterialTheme.shapes.medium else MaterialTheme.shapes.small
    AmIcons.Email
    FilterChip(
        modifier = modifier.animateContentSize(),
        selected = selected,
        label = { Text(text = label) },
        shape=shape,
        leadingIcon = { if (selected) AmIcon(amIconsType = AmIcons.Done)},
        onClick = onClick,
    )
}

@Preview
@Composable
fun AmChipPreview(){
    AmChip(
        selected = true,
        label = "TEST",
        onClick = {}
    )
}
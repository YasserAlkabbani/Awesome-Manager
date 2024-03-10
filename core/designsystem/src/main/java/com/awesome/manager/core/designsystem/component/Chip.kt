package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        border = FilterChipDefaults.filterChipBorder(borderWidth = 1.5.dp, enabled = false, selected = true),
        leadingIcon = { if (selected) AmIcon(amIconsType = AmIcons.Done)},
        onClick = onClick,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
            selectedTrailingIconColor = MaterialTheme.colorScheme.surface,
            selectedLeadingIconColor = MaterialTheme.colorScheme.surface
        )
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
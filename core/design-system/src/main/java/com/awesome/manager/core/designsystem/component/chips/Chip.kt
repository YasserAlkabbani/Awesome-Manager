package com.awesome.manager.core.designsystem.component.chips

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    label: String,
    onClick: () -> Unit,
) {
    val shape = if (selected) MaterialTheme.shapes.medium else MaterialTheme.shapes.small
    FilterChip(
        modifier = modifier,
        selected = selected,
        label = { Text(text = label) },
        shape = shape,
        onClick = onClick,
    )
}

@Preview
@Composable
fun AmChipSelectedPreview() {
    AmChip(
        selected = true,
        label = "TEST",
        onClick = {}
    )
}

@Preview
@Composable
fun AmChipUnSelectedPreview() {
    AmChip(
        selected = false,
        label = "TEST",
        onClick = {}
    )
}
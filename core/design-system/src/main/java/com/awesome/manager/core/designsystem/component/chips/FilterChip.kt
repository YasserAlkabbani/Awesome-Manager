package com.awesome.manager.core.designsystem.component.chips

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmFilterChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    amIconsType: AmIconsType.ImageVictorAmIconsType,
    label: String, value: String?,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    val shape = if (selected) MaterialTheme.shapes.medium else MaterialTheme.shapes.small
    val icon: @Composable () -> Unit = {
        AmIconButton(
            modifier = Modifier.size(20.dp),
            amIconsType = AmIcons.Close,
            onClick = onRemove
        )
    }
    ElevatedFilterChip(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .animateContentSize(),
        selected = selected, onClick = onClick,
        label = { AmText(text = value ?: label) },
        leadingIcon = { AmIcon(amIconsType = amIconsType) },
        shape = shape,
        trailingIcon = value?.let { icon },
    )
}
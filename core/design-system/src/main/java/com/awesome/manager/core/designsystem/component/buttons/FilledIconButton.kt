package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AmFilledIconButton(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType.ImageVictorAmIconsType,
    isError: Boolean,
    onClick: () -> Unit
) {
    FilledIconButton(
        modifier = modifier,
//        colors = IconButtonDefaults.filledIconButtonColors().run {
//            when (isError) {
//                true -> copy(containerColor = MaterialTheme.colorScheme.error)
//                false -> this
//            }
//        },
        content = {
            AmIcon(
                modifier = Modifier,
                amIconsType = amIconsType,
            )
        },
        onClick = onClick,
    )
}

@Preview
@Composable
fun FilledIconButtonPreview() {
    AmFilledIconButton(
        modifier = Modifier,
        amIconsType = AmIcons.ArrowBack,
        isError = true,
        onClick = {}
    )
}
package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant.SIZE_SMALL
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmCircularProgress
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmFilledTonalIconWithTextButton(
    modifier: Modifier = Modifier,
    text: String, amIconsType: AmIconsType,
    positive: Boolean, loading: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AmSurface(
            modifier = modifier, onClick = onClick,
            positive = positive, loading = loading,
            highPadding = true
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AmText(text = text, style = MaterialTheme.typography.titleMedium)
                AmCard(
                    modifier = Modifier.size(SIZE_SMALL.dp), positive = positive,
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    if (loading) {
                        AmCircularProgress(modifier = Modifier.fillMaxSize(), positive = true)
                    } else {
                        AmIcon(modifier = Modifier.fillMaxSize(), amIconsType = amIconsType)
                    }
                }
            }
        }
    }

}


@Preview
@Composable
fun AmFilledTonalIconButtonWithPositivePreview() {
    AmFilledTonalIconWithTextButton(
        modifier = Modifier,
        text = "TEST TEXT", amIconsType = AmIcons.Save,
        positive = true, loading = true,
        onClick = {}
    )
}

@Preview
@Composable
fun AmFilledTonalIconButtonWithNegativePreview() {
    AmFilledTonalIconWithTextButton(
        modifier = Modifier,
        text = "TEST TEXT", amIconsType = AmIcons.Save,
        positive = false, loading = false,
        onClick = {}
    )
}
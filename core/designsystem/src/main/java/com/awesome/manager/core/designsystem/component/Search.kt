package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.UIConstant.PADDING_MEDIUM
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmSearch(
    modifier: Modifier,
    searchLabel: String,
    syncSearchKey: (String) -> Unit, errorMessage: String?,
    showProfileBottomSheet: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = PADDING_MEDIUM.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AmTextField(
            modifier = Modifier.weight(1f),
            hint = "Search For Account", icon = AmIcons.Search, label = searchLabel,
            onTextChange = syncSearchKey,
            error = errorMessage
        )
        AmSpacerMediumWidth()
        AmSurface(
            positive = null, shape = MaterialTheme.shapes.extraLarge,
            highPadding = false, onClick = showProfileBottomSheet
        ) {
            AmIcon(
                modifier = Modifier.size(UIConstant.SIZE_MEDIUM.dp),
                amIconsType = AmIcons.Profile
            )
        }
    }
}
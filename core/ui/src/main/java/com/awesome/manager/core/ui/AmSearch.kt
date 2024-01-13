package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.UIConstant.SPACE_EXTRA_SMALL
import com.awesome.manager.core.designsystem.UIConstant.SPACE_MEDIUM
import com.awesome.manager.core.designsystem.UIConstant.SPACE_SMALL
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmSearch(
    searchKey: String, searchLabel: String,
    onSearchKeyChange: (String) -> Unit, errorMessage: String?,
    showProfileBottomSheet: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = SPACE_SMALL.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AmTextField(
            modifier = Modifier.weight(1f),
            hint = "Search For Account", icon = AmIcons.Search, label = searchLabel,
            text = searchKey,
            onTextChange = onSearchKeyChange,
            error = errorMessage
        )
        Spacer(modifier = Modifier.width(SPACE_MEDIUM.dp))
        AmSurface(
            positive = null, shape = MaterialTheme.shapes.extraLarge,
            highPadding = false, onClick = showProfileBottomSheet
        ) {
            AmIcon(
                modifier = Modifier.size(UIConstant.IMAGE_SIZE_MEDIUM.dp),
                amIconsType = AmIcons.Profile
            )
        }
    }
}
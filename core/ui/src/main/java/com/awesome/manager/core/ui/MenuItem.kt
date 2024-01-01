package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant.IMAGE_SIZE_MEDIUM
import com.awesome.manager.core.designsystem.UIConstant.SPACE_MEDIUM
import com.awesome.manager.core.designsystem.UIConstant.SPACE_SMALL
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmTextManager

@Composable
fun MenuItem(
    title: AmTextManager, subTitle: AmTextManager,
    amIconsType: AmIconsType?, imageUrl: String?,
    loading: Boolean, onClick: () -> Unit
) {

    AmCard(
        modifier = Modifier.fillMaxWidth(),
        positive = null, loading = loading, onClick = onClick
    ) {
        Row {
            imageUrl?.let { AmImage(modifier = Modifier.size(IMAGE_SIZE_MEDIUM.dp), imageUrl = it) }
            amIconsType?.let {
                AmIcon(
                    modifier = Modifier.size(IMAGE_SIZE_MEDIUM.dp), amIconsType = it
                )
            }
            Spacer(modifier = Modifier.width(SPACE_MEDIUM.dp))
            Column {
                AmText(text = title.asText(), style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.width(SPACE_SMALL.dp))
                AmText(text = subTitle.asText(), style = MaterialTheme.typography.labelMedium)
            }
        }
    }

}
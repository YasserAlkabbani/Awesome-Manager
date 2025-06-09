package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.cards.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmTextManager

@Composable
fun MenuItem(
    title: AmTextManager,
    subTitle: AmTextManager,
    amIconsType: AmIconsType.ImageVictorAmIconsType?,
    imageUrl: String?,
    loading: Boolean, onClick: () -> Unit
) {

    AmCard(
        modifier = Modifier.fillMaxWidth(),
        isPositive = null, isLoading = loading, onClick = onClick
    ) {
        Row {
            imageUrl?.let { AmImage(modifier = Modifier.size(AmSize.MEDIUM.value), imageUrl = it) }
            amIconsType?.let {
                AmIcon(
                    modifier = Modifier.size(AmSize.MEDIUM.value),
                    amIconsType = it
                )
            }
            Column {
                AmText(text = title.asText(), textStyle = MaterialTheme.typography.titleLarge)
                AmText(text = subTitle.asText(), textStyle = MaterialTheme.typography.labelMedium)
            }
        }
    }

}
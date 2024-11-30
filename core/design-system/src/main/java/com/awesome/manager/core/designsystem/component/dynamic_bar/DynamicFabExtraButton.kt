package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmDynamicFabExtraButton(
    dynamicFabExtraButton: DynamicFabExtraButton
) {
    AmIconButton(
        modifier = Modifier,
        amIconsType = dynamicFabExtraButton.amIconsType,
        onClick = dynamicFabExtraButton.onClick,
    )
}


sealed class DynamicFabExtraButton(
    val isPositave: Boolean,
    val amIconsType: AmIconsType.ImageVictorAmIconsType,
    val index: Int
) {
    abstract val onClick: () -> Unit

    data class Back(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = false, amIconsType = AmIcons.ArrowBack, index = 1)

    data class Cancel(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = false, amIconsType = AmIcons.Close, index = 2)

    data class Edit(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = true, amIconsType = AmIcons.Edit, index = 3)

    data class More(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = true, amIconsType = AmIcons.More, index = 4)

    data class TryAgain(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = false, amIconsType = AmIcons.Retry, index = 5)

}
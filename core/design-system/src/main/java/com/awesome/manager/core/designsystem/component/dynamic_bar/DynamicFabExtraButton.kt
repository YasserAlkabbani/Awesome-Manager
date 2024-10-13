package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmDynamicFabExtraButton(
    dynamicFabExtraButton: DynamicFabExtraButton
) {
    AmSurface(
        modifier = Modifier.padding(horizontal = AmPadding.X_SMALL.value),
        padding = AmPadding.X_SMALL,
        shape = MaterialTheme.shapes.extraLarge,
        onClick = dynamicFabExtraButton.onClick,
    ) {
        AmIcon(
            modifier = Modifier
                .padding(AmPadding.MEDIUM.value)
                .size(AmSize.MID_SMALL.value),
            amIconsType = dynamicFabExtraButton.amIconsType,
        )
    }
}


sealed class DynamicFabExtraButton(val isPositave: Boolean,val amIconsType: AmIconsType) {
    abstract val onClick: () -> Unit

    data class Back(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = false, amIconsType = AmIcons.ArrowBack)

    data class Cancel(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = false, amIconsType = AmIcons.Close)

    data class Edit(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = true, amIconsType = AmIcons.Edit)

    data class More(override val onClick: () -> Unit) :
        DynamicFabExtraButton(isPositave = true, amIconsType = AmIcons.More)

}
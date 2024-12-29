package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.R
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmFabButton(dynamicFabButton: DynamicFabButton) {
    AmButton(
        modifier = Modifier,
        text = stringResource(dynamicFabButton.text),
        amIconsType = AmIcons.ArrowForward,
        onClick = dynamicFabButton.onClick
    )
}


sealed class DynamicFabButton(val isPositive: Boolean, @StringRes val text: Int, val index: Int) {
    abstract val onClick: () -> Unit

    data class Login(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.start_accounting, index = 0)

    data class SearchForAccount(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.search_for_account, index = 1)

    data class Create(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.create, index = 2)

    data class Edit(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.edit, index = 3)

    data class Update(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.update, index = 4)

    data class TryAgain(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = false, text = R.string.try_again, index = 5)

}
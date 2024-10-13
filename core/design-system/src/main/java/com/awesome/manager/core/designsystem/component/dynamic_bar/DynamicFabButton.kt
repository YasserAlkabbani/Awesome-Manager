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
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmFabButton(dynamicFabButton: DynamicFabButton) {
    AmSurface(
        modifier = Modifier,
        padding = AmPadding.X_LARGE,
        positive = dynamicFabButton.isPositive,
        onClick = dynamicFabButton.onClick,
    ) {
        Row {
            AmText(text = stringResource(dynamicFabButton.text))
            AmIcon(amIconsType = AmIcons.ArrowForward)
        }
    }
}


sealed class DynamicFabButton(val isPositive: Boolean, @StringRes val text: Int) {
    abstract val onClick: () -> Unit

    data class Login(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.start_accounting)

    data class Save(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = true, text = R.string.welcome_back)

    data class Update(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = false, text = R.string.invalid_email)

    data class Edit(override val onClick: () -> Unit) :
        DynamicFabButton(isPositive = false, text = R.string.invalid_password)
}
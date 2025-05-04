package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.R
import com.awesome.manager.core.designsystem.component.surface.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmDynamicText(
    dynamicFabText: DynamicFabText
) {
    AmSurface(
        padding = AmPadding.LARGE,
        isPositive = dynamicFabText.isPositive
    ) {
        AmText(
            modifier = Modifier,
            text = stringResource(dynamicFabText.text),
            textStyle = MaterialTheme.typography.titleMedium,
        )
    }
}


sealed class DynamicFabText(val isPositive: Boolean, @StringRes val text: Int, val index: Int) {
    data object WelcomeBack :
        DynamicFabText(isPositive = true, text = R.string.welcome_back, index = 1)

    data object InvalidEmail :
        DynamicFabText(isPositive = false, text = R.string.invalid_email, index = 2)

    data object InvalidPassword :
        DynamicFabText(isPositive = false, text = R.string.invalid_password, index = 3)

    data object InvalidLoginCredential :
        DynamicFabText(isPositive = false, text = R.string.invalid_login_credential, index = 4)

    data object ConnectionError :
        DynamicFabText(isPositive = false, text = R.string.connection_error, index = 5)

    data object InvalidInput :
        DynamicFabText(isPositive = false, text = R.string.invalid_input, index = 6)

}

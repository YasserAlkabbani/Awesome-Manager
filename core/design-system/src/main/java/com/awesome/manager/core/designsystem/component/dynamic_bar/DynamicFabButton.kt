package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.R
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmFabButton(dynamicFabButton: DynamicFabButton) {
    AmButton(
        modifier = Modifier,
        text = stringResource(dynamicFabButton.text),
        isError = dynamicFabButton.isPositive,
        amIconsType = dynamicFabButton.amIconsType,
        onClick = dynamicFabButton.onClick
    )
}


sealed class DynamicFabButton(
    val isPositive: Boolean,
    @StringRes val text: Int,
    val amIconsType: AmIconsType.ImageVictorAmIconsType,
    val index: Int
) {
    abstract val onClick: () -> Unit

    data class Login(override val onClick: () -> Unit) :
        DynamicFabButton(
            isPositive = true,
            text = R.string.start_accounting,
            amIconsType = AmIcons.ArrowForward,
            index = 0
        )

    data class SearchForAccount(override val onClick: () -> Unit) :
        DynamicFabButton(
            isPositive = true,
            text = R.string.search_for_account,
            amIconsType = AmIcons.Search,
            index = 1
        )

    data class Create(override val onClick: () -> Unit) :
        DynamicFabButton(
            isPositive = true,
            text = R.string.create,
            amIconsType = AmIcons.Save,
            index = 2
        )

    data class Edit(override val onClick: () -> Unit) :
        DynamicFabButton(
            isPositive = true,
            text = R.string.edit,
            amIconsType = AmIcons.Edit,
            index = 3
        )

    data class Update(override val onClick: () -> Unit) :
        DynamicFabButton(
            isPositive = true,
            text = R.string.update,
            amIconsType = AmIcons.Save,
            index = 4
        )

    data class TryAgain(override val onClick: () -> Unit) :
        DynamicFabButton(
            isPositive = false,
            text = R.string.try_again,
            amIconsType = AmIcons.Retry,
            index = 5
        )

}
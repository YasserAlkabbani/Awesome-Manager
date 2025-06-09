package com.awesome.manager.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.buttons.AmFilledIconButton
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType

sealed interface FloatingToolbarContent {

    @Composable
    fun Content(isError: Boolean)

    data object Loading : FloatingToolbarContent {
        @Composable
        override fun Content(isError: Boolean) {
            LinearProgressIndicator(
                modifier = Modifier.height(AmSize.FLOATING_TOOLBAR_LOADING_HEIGHT.value),
            )
        }
    }

    data class Text(
        @StringRes val textRes: Int
    ) : FloatingToolbarContent {
        @Composable
        override fun Content(isError: Boolean) {
            AmText(
                modifier = Modifier,
                text = stringResource(textRes),
                textStyle = MaterialTheme.typography.titleMedium
            )
        }
    }

    data class IconButton(
        val amIconsType: AmIconsType.ImageVictorAmIconsType,
        val onClick: () -> Unit
    ) : FloatingToolbarContent {
        @Composable
        override fun Content(isError: Boolean) {
            AmFilledIconButton(
                modifier = Modifier,
                amIconsType = amIconsType,
                isError = isError,
                onClick = onClick
            )
        }
    }

    data class Button(
        @StringRes val textRes: Int,
        val amIconsType: AmIconsType.ImageVictorAmIconsType,
        val onClick: () -> Unit
    ) : FloatingToolbarContent {
        @Composable
        override fun Content(isError: Boolean) {
            AmButton(
                modifier = Modifier,
                text = stringResource(textRes),
                amIconsType = amIconsType,
                isError = isError,
                onClick = onClick
            )
        }
    }

}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
sealed interface FloatingToolBarState {

    val content: FloatingToolbarContent?
    val leading: FloatingToolbarContent?
    val trailing: FloatingToolbarContent?

    val isExpended: Boolean
    val isError: Boolean

    @Composable
    fun floatingToolBarColors(): FloatingToolbarColors

    data object Loading : FloatingToolBarState {
        override val content: FloatingToolbarContent? = FloatingToolbarContent.Loading
        override val leading: FloatingToolbarContent? = null
        override val trailing: FloatingToolbarContent? = null

        override val isExpended = trailing != null || leading != null
        override val isError = false

        @Composable
        override fun floatingToolBarColors() =
            FloatingToolbarDefaults.vibrantFloatingToolbarColors()

    }

    data class Error(
        override val content: FloatingToolbarContent? = null,
        override val trailing: FloatingToolbarContent? = null,
        override val leading: FloatingToolbarContent? = null,
    ) : FloatingToolBarState {

        override val isExpended = trailing != null || leading != null
        override val isError = true

        @Composable
        override fun floatingToolBarColors() =
            FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                toolbarContainerColor = MaterialTheme.colorScheme.errorContainer,
            )

    }

    data class Content(
        override val content: FloatingToolbarContent?
    ) : FloatingToolBarState {
        override val leading: FloatingToolbarContent? = null
        override val trailing: FloatingToolbarContent? = null

        override val isExpended = trailing != null || leading != null
        override val isError = false

        @Composable
        override fun floatingToolBarColors() =
            FloatingToolbarDefaults.vibrantFloatingToolbarColors()

    }

}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.AMHorizontalFloatingToolbar(
    floatingToolBarState: FloatingToolBarState
) {
    HorizontalFloatingToolbar(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .safeContentPadding(),
        expanded = floatingToolBarState.isExpended,
        leadingContent = {
            AnimatedContent(targetState = floatingToolBarState.leading) { leading ->
                leading?.Content(floatingToolBarState.isError)
            }
        },
        trailingContent = {
            AnimatedContent(targetState = floatingToolBarState.trailing) { trailing ->
                trailing?.Content(floatingToolBarState.isError)
            }
        },
        colors = floatingToolBarState.floatingToolBarColors(),
        content = {
            AnimatedContent(targetState = floatingToolBarState.content) { content ->
                content?.Content(floatingToolBarState.isError)
            }
        },
    )
}

@Preview(
    device = Devices.PIXEL_9,
    showBackground = true
)
@Composable
fun HorizontalFloatingToolbarPreview() {
    Box(Modifier.fillMaxSize()) {
        AMHorizontalFloatingToolbar(
            floatingToolBarState = FloatingToolBarState.Loading
        )
    }
}
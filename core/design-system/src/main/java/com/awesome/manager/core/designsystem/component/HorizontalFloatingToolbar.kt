package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.buttons.AmFilledIconButton
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmText

sealed interface FloatingToolbarComponent {

    @Composable
    fun Content(isError: Boolean)

    data object Empty : FloatingToolbarComponent {
        @Composable
        override fun Content(isError: Boolean) {
            AmSpacerWidth(AmPadding.SMALL)
        }
    }

    data object Loading : FloatingToolbarComponent {
        @Composable
        override fun Content(isError: Boolean) {
            AmCircularProgressIndicator()
        }
    }

    data class Text(
        val amText: AmText
    ) : FloatingToolbarComponent {
        @Composable
        override fun Content(isError: Boolean) {
            AmText(
                modifier = Modifier,
                text = amText.asText(),
                textStyle = MaterialTheme.typography.titleMedium
            )
        }
    }

    data class IconButton(
        val amIconsType: AmIconsType.ImageVictorAmIconsType,
        val onClick: () -> Unit
    ) : FloatingToolbarComponent {
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

    data class ActionButton(
        val text: AmText,
        val amIconsType: AmIconsType.ImageVictorAmIconsType,
        val onClick: () -> Unit
    ) : FloatingToolbarComponent {
        @Composable
        override fun Content(isError: Boolean) {
            AmButton(
                modifier = Modifier,
                text = text.asText(),
                amIconsType = amIconsType,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
sealed interface FloatingToolBarState {

    val leading: FloatingToolbarComponent?
    val leading2: FloatingToolbarComponent?
    val content: FloatingToolbarComponent
    val trailing: FloatingToolbarComponent?

    val isExpended: Boolean
    val isError: Boolean

    @Composable
    fun floatingToolBarColors(): FloatingToolbarColors

    data object Loading : FloatingToolBarState {
        override val leading: FloatingToolbarComponent? = null
        override val leading2: FloatingToolbarComponent? = null
        override val content: FloatingToolbarComponent = FloatingToolbarComponent.Loading
        override val trailing: FloatingToolbarComponent? = null

        override val isExpended = false
        override val isError = false

        @Composable
        override fun floatingToolBarColors() =
            FloatingToolbarDefaults.vibrantFloatingToolbarColors()

    }

    data class Error(
        val errorMessage: AmText,
        val backButton: FloatingToolbarComponent.IconButton? = null,
        val retryButton: FloatingToolbarComponent.IconButton? = null
    ) : FloatingToolBarState {

        override val leading: FloatingToolbarComponent.IconButton? = backButton
        override val leading2: FloatingToolbarComponent.IconButton? = null
        override val content: FloatingToolbarComponent.Text =
            FloatingToolbarComponent.Text(errorMessage)
        override val trailing: FloatingToolbarComponent.IconButton? = retryButton

        override val isExpended = trailing != null || leading != null
        override val isError = true

        @Composable
        override fun floatingToolBarColors() =
            FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                toolbarContainerColor = MaterialTheme.colorScheme.errorContainer,
                toolbarContentColor = MaterialTheme.colorScheme.onErrorContainer
            )

    }

    data class Content(
        val textMessage: AmText?,
        val backButton: FloatingToolbarComponent.IconButton? = null,
        val editButton: FloatingToolbarComponent.IconButton? = null,
        val actionButton: FloatingToolbarComponent.ActionButton? = null,
    ) : FloatingToolBarState {
        override val leading: FloatingToolbarComponent? = backButton
        override val leading2: FloatingToolbarComponent? = editButton
        override val content: FloatingToolbarComponent = textMessage
            ?.let { FloatingToolbarComponent.Text(amText = it) }
            ?: FloatingToolbarComponent.Empty
        override val trailing: FloatingToolbarComponent? = actionButton

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
            AnimatedContent(targetState = floatingToolBarState.leading2) { leading2 ->
                leading2?.Content(floatingToolBarState.isError)
            }
        },
        content = {
            AnimatedContent(targetState = floatingToolBarState.content) { content ->
                content.Content(floatingToolBarState.isError)
            }
        },
        trailingContent = {
            AnimatedContent(targetState = floatingToolBarState.trailing) { trailing ->
                trailing?.Content(floatingToolBarState.isError)
            }
        },
        colors = floatingToolBarState.floatingToolBarColors(),
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
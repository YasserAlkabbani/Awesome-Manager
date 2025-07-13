package com.awesome.manager.core.designsystem.component.text

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecureTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmSecureTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    icon: AmIconsType.ImageVictorAmIconsType? = null,
    placeHolder: String,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: () -> Unit = {},
    isValidateInput: Boolean
) {

    val focusRequested: MutableState<Boolean> = remember { mutableStateOf(false) }
    var passwordHidden: MutableState<Boolean> = remember { mutableStateOf(true) }
    val validateInput: Boolean = remember(textFieldState.text, isValidateInput) {
        when {
            isValidateInput -> true
            !focusRequested.value -> true
            else -> false
        }
    }
    SecureTextField(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = AmSize.TEXT_FIELD_MINIMUM_HEIGHT.value)
            .onFocusChanged { if (it.hasFocus) focusRequested.value = true },
        state = textFieldState,
        enabled = enabled,
        placeholder = { AmText(text = placeHolder) },
        leadingIcon = {
            icon?.let {
                AmIcon(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    amIconsType = icon
                )
            }
        },
        trailingIcon = {
            AmIconButton(
                modifier = Modifier.size(AmSize.SMALL.value),
                onClick = { passwordHidden.value = !passwordHidden.value },
                amIconsType = when (passwordHidden.value) {
                    true -> AmIcons.VisibilityOff
                    false -> AmIcons.Visibility
                },
            )
        },
        textStyle = MaterialTheme.typography.titleMedium,
        shape = MaterialTheme.shapes.large,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = KeyboardActionHandler({
            it()
            onKeyboardAction()
        }),
        isError = !validateInput,
        contentPadding = PaddingValues(0.dp),
        textObfuscationMode = when (passwordHidden.value) {
            true -> TextObfuscationMode.Visible
            false -> TextObfuscationMode.RevealLastTyped
        },
    )

}

@Preview
@Composable
fun SecureTextFieldPreview() {
    AmSecureTextField(
        modifier = Modifier,
        textFieldState = TextFieldState(),
        icon = AmIcons.Search,
        placeHolder = "PLACE HOLDER",
        enabled = false,
        keyboardOptions = KeyboardOptions.Default,
        onKeyboardAction = {},
        isValidateInput = true,
    )
}
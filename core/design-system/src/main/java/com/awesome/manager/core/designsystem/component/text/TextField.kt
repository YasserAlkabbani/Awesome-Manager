package com.awesome.manager.core.designsystem.component.text

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerMediumWidth
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import java.lang.Error

@Composable
fun AmTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    text: String,
    icon: AmIconsType.ImageVictorAmIconsType? = null,
    label: String? = null,
    hint: String,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    formatText: String.() -> String = { this }
) {

    val textFieldValue: MutableState<TextFieldValue> = remember {
        mutableStateOf(TextFieldValue(text, TextRange(text.length)))
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        value = textFieldValue.value,
        enabled = enabled,
        placeholder = { AmText(text = hint) },
        label = label?.let { { AmText(text = label) } },
        onValueChange = {
            val formattedText = it.text.formatText()
            textFieldValue.value = it.copy(formattedText)
            onTextChange(formattedText)
        },
        leadingIcon = {
            icon?.let {
                AmIcon(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    amIconsType = icon
                )
            }
        },
        textStyle = MaterialTheme.typography.titleMedium,
        shape = MaterialTheme.shapes.large,
        singleLine = singleLine,
        maxLines = 3,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
//        colors = TextFieldDefaults.colors(
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            errorIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//            focusedContainerColor = MaterialTheme.colorScheme.surface,
//            errorContainerColor = MaterialTheme.colorScheme.surface,
//            disabledContainerColor = MaterialTheme.colorScheme.surface,
//            unfocusedContainerColor = MaterialTheme.colorScheme.surface
//        ),
    )
    /*    Surface(
            modifier = modifier, color = color,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(Modifier.padding(AmPadding.X_SMALL.value)) {
                if (label != null && icon != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AmPadding.MEDIUM.value)
                            .padding(bottom = AmPadding.SMALL.value),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AmIcon(modifier = Modifier.height(IntrinsicSize.Max), amIconsType = icon)
                        AmSpacerMediumWidth()
                        AmText(
                            modifier = Modifier.wrapContentHeight(),
                            text = label,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .onFocusChanged { isFocus.value = it.hasFocus }
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small
                        ),
                    value = text,
                    enabled = enabled,
                    placeholder = { AmText(text = hint) },
                    onValueChange = onTextChange,
                    textStyle = MaterialTheme.typography.titleMedium,
                    shape = MaterialTheme.shapes.medium,
                    singleLine = singleLine,
                    maxLines = 3,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        errorContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                )
            }
        }*/
}

@Composable
fun AmPasswordTextField(
    modifier: Modifier = Modifier, onTextChange: (String) -> Unit,
    icon: AmIconsType.ImageVictorAmIconsType? = null, label: String? = null, hint: String,
    singleLine: Boolean = true, enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {

    val text = rememberSaveable { mutableStateOf("") }

    val isFocus = remember { mutableStateOf(false) }
    val color = animateColorAsState(
        targetValue = if (isFocus.value) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.secondaryContainer,
        label = "1"
    ).value
    var passwordHidden by remember { mutableStateOf(true) }

    Surface(
        modifier = modifier, color = color,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(AmPadding.SMALL.value)) {
            if (label != null && icon != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AmPadding.MEDIUM.value)
                        .padding(bottom = AmPadding.SMALL.value),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AmIcon(modifier = Modifier.height(IntrinsicSize.Max), amIconsType = icon)
                    AmSpacerMediumWidth()
                    AmText(
                        modifier = Modifier.wrapContentHeight(),
                        text = label,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onFocusChanged { isFocus.value = it.hasFocus }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.small
                    ),
                value = text.value,
                enabled = enabled,
                placeholder = { AmText(text = hint) },
                onValueChange = {
                    text.value = it
                    onTextChange(it)
                },
                textStyle = MaterialTheme.typography.titleMedium,
                shape = MaterialTheme.shapes.medium,
                singleLine = singleLine,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    AmIconButton(
                        modifier = Modifier.size(AmSize.SMALL.value),
                        onClick = { passwordHidden = !passwordHidden },
                        amIconsType = if (passwordHidden) AmIcons.VisibilityOff else AmIcons.Visibility,
                        isPositive = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    errorContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
            )
        }
    }
}


@Preview
@Composable
fun AmTextFieldPreview() {
    AmTextField(
        modifier = Modifier,
        hint = "HINT",
        icon = AmIcons.Email,
        label = "LABEL",
        onTextChange = {},
        text = "",
        isError = false
    )
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun CustomTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    textStyle: TextStyle = LocalTextStyle.current,
//    label: @Composable (() -> Unit)? = null,
//    placeholder: @Composable (() -> Unit)? = null,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    prefix: @Composable (() -> Unit)? = null,
//    suffix: @Composable (() -> Unit)? = null,
//    supportingText: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions.Default,
//    singleLine: Boolean = false,
//    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
//    minLines: Int = 1,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    shape: Shape = TextFieldDefaults.shape,
//    colors: TextFieldColors = TextFieldDefaults.colors()
//) {
//    // If color is not provided via the text style, use content color as a default
//    val textColor = textStyle.color.takeOrElse {
//        if (isError) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface
//    }
//    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
//
//    CompositionLocalProvider() {
//        BasicTextField(
//            value = value,
//            modifier = modifier.wrapContentHeight()
////                .defaultMinSize(minWidth = TextFieldDefaults.MinWidth, minHeight = TextFieldDefaults.MinHeight)
//            ,
//            onValueChange = onValueChange,
//            enabled = enabled,
//            readOnly = readOnly,
//            textStyle = mergedTextStyle,
//            cursorBrush = SolidColor(if (isError) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error),
//            visualTransformation = visualTransformation,
//            keyboardOptions = keyboardOptions,
//            keyboardActions = keyboardActions,
//            interactionSource = interactionSource,
//            singleLine = singleLine,
//            maxLines = maxLines,
//            minLines = minLines,
//            decorationBox = @Composable { innerTextField ->
//                // places leading icon, text field with label and placeholder, trailing icon
//                TextFieldDefaults.DecorationBox(
//                    value = value,
//                    visualTransformation = visualTransformation,
//                    innerTextField = innerTextField,
//                    placeholder = placeholder,
//                    label = label,
//                    leadingIcon = leadingIcon,
//                    trailingIcon = trailingIcon,
//                    prefix = prefix,
//                    suffix = suffix,
//                    supportingText = supportingText,
//                    shape = shape,
//                    singleLine = singleLine,
//                    enabled = enabled,
//                    isError = isError,
//                    interactionSource = interactionSource,
//                    colors = colors,
//                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
//                )
//            }
//        )
//    }
//}
package com.awesome.manager.core.designsystem.component

import android.text.InputType
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.forEachTextValue
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant.PADDING_LOW
import com.awesome.manager.core.designsystem.UIConstant.PADDING_LOW_EXTRA
import com.awesome.manager.core.designsystem.UIConstant.PADDING_MEDIUM
import com.awesome.manager.core.designsystem.UIConstant.SIZE_EXTRA_SMALL
import com.awesome.manager.core.designsystem.UIConstant.SIZE_MEDIUM
import com.awesome.manager.core.designsystem.UIConstant.SIZE_SMALL
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import kotlinx.coroutines.newSingleThreadContext

@Composable
fun AmTextField(
    modifier: Modifier = Modifier,
    initTextValue: String = "", onTextChange: (String) -> Unit,
    icon: AmIconsType, label: String, hint: String,
    singleLine: Boolean = true, error: String?, password: Boolean = false, enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {

    var text by rememberSaveable { mutableStateOf(initTextValue) }
    LaunchedEffect(key1 = text, block = { onTextChange(text) })

    val isFocus = remember { mutableStateOf(false) }
    val color = animateColorAsState(
        targetValue = if (isFocus.value)
            if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
        else
            if (error != null) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
        label = "1"
    ).value
    var passwordHidden by rememberSaveable { mutableStateOf(true) }


    Surface(
        modifier = modifier,
        color = color,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(PADDING_LOW.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PADDING_MEDIUM.dp)
                    .padding(bottom = PADDING_LOW.dp),
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
                onValueChange = { text = it },
                textStyle = MaterialTheme.typography.titleMedium,
                shape = MaterialTheme.shapes.medium,
                singleLine = singleLine,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = if (passwordHidden && password) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = if (password) {
                    {
                        AmIconButton(
                            modifier = Modifier.size(SIZE_SMALL.dp),
                            onClick = { passwordHidden = !passwordHidden },
                            positive = null,
                            amIconsType = if (passwordHidden) AmIcons.VisibilityOff else AmIcons.Visibility
                        )
                    }
                } else null,
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
            AnimatedVisibility(visible = error != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PADDING_MEDIUM.dp)
                        .padding(top = PADDING_LOW.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AmIcon(
                        modifier = Modifier.height(SIZE_EXTRA_SMALL.dp),
                        amIconsType = AmIcons.Error
                    )
                    AmSpacerMediumWidth()
                    AmText(
                        modifier = Modifier.wrapContentHeight(),
                        text = error.orEmpty(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
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
        error = "ERROR MESSAGE",
        onTextChange = {}
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
package com.awesome.manager.core.designsystem.component

import android.text.InputType
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import kotlinx.coroutines.newSingleThreadContext

@Composable
fun AmTextField(
    modifier: Modifier = Modifier,
    hint: String,
    icon: AmIconsType,
    label: String,
    text: String,
    singleLine: Boolean=true,error:String?,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation:VisualTransformation = VisualTransformation.None,
    onTextChange: (String) -> Unit
) {
    val isFocus = remember { mutableStateOf(false) }
    val shape = if (isFocus.value) MaterialTheme.shapes.medium else MaterialTheme.shapes.small
    val padding =
        animateDpAsState(targetValue = if (isFocus.value) 4.dp else 3.dp, label = "0").value
    val color = animateColorAsState(
        targetValue = if (isFocus.value)
            if (error!=null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
        else
            if (error!=null) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
        label = "1"
    ).value

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = shape
    ) {
        Column(Modifier.padding(padding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmIcon(modifier=Modifier.height(IntrinsicSize.Max),amIconsType = icon)
                Spacer(modifier = Modifier.width(4.dp))
                AmText(modifier = Modifier.wrapContentHeight(),text = label, style = MaterialTheme.typography.titleMedium)
            }
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
//                    .defaultMinSize(
//                        minWidth = 0.dp,
//                        minHeight = 0.dp
//                    )
                    .wrapContentHeight()
                    .onFocusChanged { isFocus.value = it.hasFocus },
                value = text,
                onValueChange = onTextChange,
                placeholder = { AmText(modifier,text = hint, style = MaterialTheme.typography.bodyLarge) },
                textStyle = MaterialTheme.typography.titleMedium,
                shape = shape,
                singleLine = singleLine,
                keyboardOptions=keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation=visualTransformation,
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
            AnimatedVisibility(visible = error!=null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AmIcon(modifier=Modifier.height(IntrinsicSize.Max),amIconsType = AmIcons.Error)
                    Spacer(modifier = Modifier.width(4.dp))
                    AmText(modifier = Modifier.wrapContentHeight(),text = error.orEmpty(), style = MaterialTheme.typography.titleMedium)
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
        text = "TEXT",
        label = "LABEL",
        onTextChange = {},
        error = "ERROR MESSAGE"
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        if (isError) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider() {
        BasicTextField(
            value = value,
            modifier = modifier.wrapContentHeight()
//                .defaultMinSize(minWidth = TextFieldDefaults.MinWidth, minHeight = TextFieldDefaults.MinHeight)
            ,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(if (isError) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = @Composable { innerTextField ->
                // places leading icon, text field with label and placeholder, trailing icon
                TextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    shape = shape,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        )
    }
}
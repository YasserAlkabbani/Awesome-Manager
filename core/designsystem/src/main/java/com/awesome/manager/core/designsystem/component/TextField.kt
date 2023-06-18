package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AmTextField(
    modifier: Modifier = Modifier,
    hint: String,
    icon: AmIconsType,
    label: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    val isFocus= remember {mutableStateOf(false)}
    val shape=if (isFocus.value)MaterialTheme.shapes.medium else MaterialTheme.shapes.small
    val padding= animateDpAsState(targetValue = if (isFocus.value) 6.dp else 4.dp, label = "0").value
    val color= animateColorAsState(
        targetValue = if (isFocus.value)MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer,
        label = "1"
    ).value

    AmCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape=shape
    ) {
        Column(Modifier.padding(padding)) {
            Row (modifier= Modifier
                .fillMaxWidth()
                .padding(padding)){
                AmIcon(amIconsType = icon)
                AmText(text = hint, style = MaterialTheme.typography.titleMedium)
            }
            TextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocus.value = it.hasFocus },
                value = text,
                onValueChange = onTextChange,
                placeholder = { AmText(text = label, style = MaterialTheme.typography.bodyLarge) },
                textStyle = MaterialTheme.typography.titleMedium,
                shape = shape,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    errorContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor =MaterialTheme.colorScheme.surface
                ),
            )
        }
    }
}


@Preview
@Composable
fun AmTextFieldPreview(){
    AmTextField(
        modifier = Modifier,
        hint = "HINT",
        icon = AmIcons.Email,
        text = "TEXT",
        label = "LABEL",
        onTextChange = {}
    )
}
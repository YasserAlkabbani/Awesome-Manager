package com.awesome.manager.core.designsystem.component.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmSearchTextField(
    searchLabel: String, initSearch: String,
    onSearchKeyChange: (String) -> Unit,
    onSearchDone: () -> Unit
) {
    val text = rememberSaveable { mutableStateOf(initSearch) }
    val focusRequester: FocusRequester = FocusRequester()
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AmPadding.MEDIUM.value)
            .focusRequester(focusRequester)
            .onGloballyPositioned {
                focusRequester.requestFocus()
            },
        value = text.value,
        onValueChange = {
            text.value = it
            onSearchKeyChange(it)
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchDone()
            },
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        prefix = { AmIcon(amIconsType = AmIcons.Search) },
        placeholder = { AmText(text = searchLabel) },
        colors = TextFieldDefaults.colors().copy(
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.extraLarge
    )

}


@Preview
@Composable
fun AmSearchTextFieldPreview() {
    AmSearchTextField("Search For Something", "", {}, {})
}
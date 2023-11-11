package com.awesome.manager.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun SearchBox(
    searchKey: String, label: String,
    onSearchKeyChange: (String) -> Unit, errorMessage: String?
) {
//    val focusRequester: FocusRequester = FocusRequester()
    AmTextField(
        modifier = Modifier
//            .focusRequester(focusRequester)
//            .onGloballyPositioned {
//                focusRequester.requestFocus()
//            }
        ,
        hint = "Search For Account", icon = AmIcons.Search, label = label,
        text = searchKey,
        onTextChange = onSearchKeyChange,
        error = errorMessage
    )
}
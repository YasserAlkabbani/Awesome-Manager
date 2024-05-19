package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.ui_actions.bottomsheet.BottomSheetAction

@Composable
fun BottomSheetSearchForAccount(searchForAccount: BottomSheetAction.SearchForAccount) {
    val focusRequester: FocusRequester = FocusRequester()
    AmTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .onGloballyPositioned {
                focusRequester.requestFocus()
            },
        hint = "Search For ..", icon = AmIcons.Search, label = "Search for an account",
        error = null,
        onTextChange = searchForAccount.onReSearch
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = UIConstant.SCROLL_CONTENT_PADDING_BOTTOM.dp),
        verticalArrangement = Arrangement.spacedBy(UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS.dp),
        content = searchForAccount.items
    )
}
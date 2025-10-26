package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn

enum class SearchType {
    ACCOUNT,
    TRANSACTION;

    val id: String = name

    companion object {
        fun getSearchType(searchTypeID: String) =
            SearchType.entries.firstOrNull { it.id == searchTypeID }
    }
}

@Composable
fun BottomSheetSearch(
    searchLabel: String,
    searchHint: String,
    searchTypes: List<SearchType>,
    selectedSearchType: SearchType,
    onUpdateSearchType: (SearchType) -> Unit,
    searchKey: String,
    onUpdateSearchKey: (String) -> Unit,
    onSearchDone: () -> Unit,
    items: LazyListScope.() -> Unit
) {
    val searchTypeChipData = remember {
        searchTypes.map { ChipData(id = it.id, title = it.name) }
    }
    val focusRequester: FocusRequester = FocusRequester()

    Column {
        AmTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onGloballyPositioned { focusRequester.requestFocus() },
            textFieldState = TextFieldState(),
            placeHolder = searchLabel,
            onKeyboardAction = onSearchDone,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            icon = AmIcons.Search,
            isValidateInput = true
        )
        AmChipsContainer(
            title = "Filter For ..",
            chipDataList = searchTypeChipData,
            selectedItemID = selectedSearchType.id,
            onSelect = { SearchType.getSearchType(it.id)?.let { onUpdateSearchType(it) } },
            content = null
        )
        AmLazyColumn(
            onRefresh = {},
            isRefreshing = false,
            content = items,
        )
    }
}
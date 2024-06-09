package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.component.text.AmSearchTextField

@Composable
fun BottomSheetSearchWithContent(
    searchWithContent: BottomSheetContent.SearchWithContent
) {
    AmSearchTextField(
        searchLabel = searchWithContent.searchLabel,
        initSearch = searchWithContent.initSearch,
        onSearchKeyChange = searchWithContent.onReSearch,
        onSearchDone = searchWithContent.onSearchDone
    )
    Spacer(modifier = Modifier.height(8.dp))
    searchWithContent.content()
}
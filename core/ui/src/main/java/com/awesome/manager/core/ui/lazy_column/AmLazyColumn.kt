package com.awesome.manager.core.ui.lazy_column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmLazyColumnPadding


const val LAZY_ITEM_ACCOUNT = "LAZY_ITEM_ACCOUNT"
const val LAZY_ITEM_TRANSACTION = "LAZY_ITEM_TRANSACTION"
const val LAZY_ITEM_HOME = "LAZY_ITEM_HOME"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmLazyColumn(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = AmLazyColumnPadding.PADDING_BOTTOM.value),
            content = content
        )
    }

}
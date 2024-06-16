package com.awesome.manager.core.ui.lazy_column

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmLazyColumnPadding
import com.awesome.manager.core.designsystem.component.AmLinearProgress


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

    LaunchedEffect(key1 = isRefreshing) {
        if (!isRefreshing) pullToRefreshState.endRefresh()
    }
    LaunchedEffect(key1 = pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) onRefresh()
    }

    Column(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        AnimatedVisibility(pullToRefreshState.isRefreshing) { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = AmLazyColumnPadding.PADDING_BOTTOM.value),
            verticalArrangement = Arrangement.spacedBy(AmLazyColumnPadding.SPACE_BETWEEN_ITEM.value),
            content = content
        )
    }
//    PullToRefreshBox(
//        modifier = Modifier,
//        state = pullToRefreshState,
//        isRefreshing = isRefreshing,
//        onRefresh = onRefresh,
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(bottom = AmLazyColumnPadding.PADDING_BOTTOM.value),
//            verticalArrangement = Arrangement.spacedBy(AmLazyColumnPadding.SPACE_BETWEEN_ITEM.value),
//            content = content
//        )
//    }

}
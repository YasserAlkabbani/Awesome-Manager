package com.awesome.manager.core.ui.lazy_column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmLazyColumnPadding


const val LAZY_ITEM_ACCOUNT = "LAZY_ITEM_ACCOUNT"
const val LAZY_ITEM_TRANSACTION = "LAZY_ITEM_TRANSACTION"
const val LAZY_ITEM_HOME = "LAZY_ITEM_HOME"

@Composable
fun AmLazyColumn(
    content: LazyListScope.() -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = AmLazyColumnPadding.PADDING_BOTTOM.value),
        verticalArrangement = Arrangement.spacedBy(AmLazyColumnPadding.SPACE_BETWEEN_ITEM.value),
        content = content
    )

}
package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard

@Composable
fun AmBottomSheetContainer(content: @Composable ColumnScope.() -> Unit) {
    AmCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        content = content,
        balance=null
    )
}
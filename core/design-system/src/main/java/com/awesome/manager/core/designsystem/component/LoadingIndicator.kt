package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize

@Composable
fun BoxScope.AmLoadingIndicator(
    isLoading: Boolean,
    isPositive: Boolean?
) {
    val primaryContainer = MaterialTheme.colorScheme.primary
    val secondaryContainer = MaterialTheme.colorScheme.secondary
    val errorContainer = MaterialTheme.colorScheme.error
    val cardColors = remember(isPositive) {
        when (isPositive) {
            null -> secondaryContainer
            true -> primaryContainer
            false -> errorContainer
        }
    }
    AnimatedContent(
        targetState = isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(AmSize.LOADING_INDICATOR_HEIGHT.value)
            .align(Alignment.BottomCenter),
        label = "CARD_INDICATOR_BOTTOM",
    ) { loading ->
        when (loading) {
            true -> AmLinearProgress(
                modifier = Modifier.fillMaxSize(),
                isPositive = isPositive,
            )

            false -> Surface(
                modifier = Modifier.fillMaxSize(),
                color = cardColors
            ) {}
        }
    }
}

@Preview
@Composable
fun AmLoadingIndicatorPreview() {
    Box {
        AmLoadingIndicator(
            isPositive = true,
            isLoading = false
        )
    }
}
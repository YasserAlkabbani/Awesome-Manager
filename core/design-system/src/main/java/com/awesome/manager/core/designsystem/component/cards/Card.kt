package com.awesome.manager.core.designsystem.component.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmLoadingIndicator
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: AmPadding = AmPadding.MEDIUM,
    isPositive: Boolean? = null,
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val cardColors = remember(isPositive) {
        when (isPositive) {
            null -> secondaryContainer
            true -> primaryContainer
            false -> errorContainer
        }
    }
    Box(
        modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max)
            .clip(shape)
    ) {
        AmLoadingIndicator(
            isLoading = isLoading,
            isPositive = isPositive
        )
        Card(
            modifier = Modifier.padding(bottom = AmPadding.CARD_SURFACE_INDICATOR.value),
            colors = CardDefaults.cardColors(containerColor = cardColors),
            shape = shape,
            content = {
                Column(
                    modifier = Modifier.padding(padding.value),
                    content = {
                        content()
                    }
                )
            },
        )
    }
}

@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: AmPadding = AmPadding.MEDIUM,
    isPositive: Boolean? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val cardColors = remember(isPositive) {
        when (isPositive) {
            null -> secondaryContainer
            true -> primaryContainer
            false -> errorContainer
        }
    }
    Box(
        modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max)
            .clip(shape),
    ) {
        AmLoadingIndicator(
            isLoading = isLoading,
            isPositive = isPositive
        )
        Card(
            modifier = Modifier
                .padding(bottom = AmPadding.CARD_SURFACE_INDICATOR.value)
            ,
            colors = CardDefaults.cardColors(containerColor = cardColors),
            shape = shape,
            onClick = onClick,
            content = {
                Column(
                    modifier = Modifier.padding(padding.value),
                    verticalArrangement = Arrangement.Bottom,
                    content = {
                        content()
                    }
                )
            }
        )
    }
}

@Preview
@Composable
fun AmCardPreview() {
    AmCard(
        content = {
            Column {
                AmText(text = "TEXT 1")
//                AmText(text = "TEXT 2")
//                AmText(text = "TEXT 3")
            }
        },
        isLoading = false,
        isPositive = true,
    )
}
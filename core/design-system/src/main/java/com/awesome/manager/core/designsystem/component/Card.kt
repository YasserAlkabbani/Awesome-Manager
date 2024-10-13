package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium, padding: AmPadding = AmPadding.MEDIUM,
    positive: Boolean? = null, loading: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val cardColors = remember(positive) {
        when (positive) {
            null -> secondaryContainer
            true -> primaryContainer
            false -> errorContainer
        }
    }
    when (onClick) {
        null -> {
            Card(
                modifier = modifier,
                content = {
                    Column(
                        modifier = modifier.padding(padding.value),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        content()
                    }
                    AnimatedVisibility(visible = loading) {
                        AmLinearProgress(
                            modifier = Modifier.fillMaxWidth(),
                            positive = positive
                        )
                    }
                },
                colors = CardDefaults.cardColors(containerColor = cardColors),
                shape = shape,
//                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            )
        }

        else -> {
            Card(
                modifier = modifier,
                onClick = onClick,
                content = {
                    Column(
                        modifier = modifier.padding(padding.value),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        content()
                    }
                    AnimatedVisibility(visible = loading) {
                        AmLinearProgress(
                            modifier = Modifier.fillMaxWidth(),
                            positive = positive
                        )
                    }
                },
                colors = CardDefaults.cardColors(containerColor = cardColors),
                shape = shape
            )
        }
    }


}

@Preview
@Composable
fun AmCardPreview() {
    AmCard(
        content = {
            Column {
                AmText(text = "TEXT 1")
                AmText(text = "TEXT 2")
                AmText(text = "TEXT 3")
            }
        },
        loading = true,
        positive = true
    )
}
package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.chips.AmChip
import com.awesome.manager.core.designsystem.component.text.AmText

data class ChipData(val id: String, val title: String)

@Composable
fun AmChipsContainer(
    title: String,
    chipDataList: List<ChipData>,
    selectedItem: String?,
    onSelect: (ChipData) -> Unit,
    content: (@Composable () -> Unit)?
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AmText(
            modifier = Modifier.padding(horizontal = AmPadding.HORIZONTAL_PADDING.value),
            text = title,
            textStyle = MaterialTheme.typography.titleLarge
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = AmPadding.HORIZONTAL_PADDING.value)
        ) {
            items(
                items = chipDataList,
                key = { it.id },
                contentType = { "CHIP_DATA" }) { chipData ->
                AmChip(
                    selected = chipData.id == selectedItem, label = chipData.title,
                    onClick = { onSelect(chipData) }
                )
            }
        }
        content?.invoke()
    }

}

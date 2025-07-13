package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.chips.AmChip
import com.awesome.manager.core.designsystem.component.chips.AmChipPosation
import com.awesome.manager.core.designsystem.component.text.AmText

data class ChipData(
    val id: String,
    val titleRes: Int? = null,
    val title: String
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AmChipsContainer(
    title: String,
    chipDataList: List<ChipData>,
    selectedItemID: String?,
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
            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
            contentPadding = PaddingValues(horizontal = AmPadding.HORIZONTAL_PADDING.value)
        ) {
            itemsIndexed(
                items = chipDataList,
                key = { index, item -> item.id },
                contentType = { index, item -> "CHIP_DATA" },
                itemContent = { index, chipData ->
                    AmChip(
                        isSelected = chipData.id == selectedItemID,
                        title = (chipData.titleRes?.let { stringResource(it) } ?: chipData.title),
                        amChipPosition = when (index) {
                            0 -> AmChipPosation.FIRST
                            chipDataList.size - 1 -> AmChipPosation.LAST
                            else -> AmChipPosation.MID
                        },
                        onClick = { onSelect(chipData) }
                    )
                }
            )
        }
        content?.invoke()
    }

}

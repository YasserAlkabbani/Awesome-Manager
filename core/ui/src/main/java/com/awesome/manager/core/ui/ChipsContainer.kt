package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmChip
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText

data class ChipData(val id:String,val title:String)

@Composable
fun AmChipsContainer (
    title:String,
    chipDataList: List<ChipData>,
    selectedItem:String?,
    onSelect:(String)->Unit,
    content:(@Composable ()->Unit)?
){

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)){
            AmText(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(items = chipDataList, key = {it.id}, contentType = {"CHIP_DATA"}){chipData->
                    AmChip(
                        selected = chipData.id==selectedItem, label = chipData.title,
                        onClick = {onSelect(chipData.id)}
                    )
                }
            }
            AmSurface(modifier = Modifier.padding(4.dp),balance = null) {
                content?.invoke()
            }
        }
    }

}

package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCard(
    modifier: Modifier,
    title:String,subTitle:String,amount:Double,pending:Boolean,
    date:String,transactionType:String, isPay:Boolean,currency:String,
    createdBy:String, onClick:()->Unit
) {

    AmCard(
        modifier=modifier,
        content = {
            Row(modifier = Modifier.fillMaxWidth()) {
                AmText(modifier = Modifier.fillMaxWidth().weight(1f),text = title, style = MaterialTheme.typography.titleLarge)
                AmText(modifier = Modifier.wrapContentWidth(),text = amount.toString()+currency, style = MaterialTheme.typography.titleLarge)
            }
            AmText(text = subTitle)
            Row {
                AmTitleWithSubtitle(modifier = Modifier.fillMaxWidth().weight(1f),title = "Type", subtitle = transactionType)
//                AmTitleWithSubtitle(modifier = Modifier.fillMaxWidth().weight(1f),title = "Creator", subtitle = createdBy)
                AmTitleWithSubtitle(modifier = Modifier.fillMaxWidth().weight(1f),title = "Date", subtitle = date)
            }
        },
        positive= isPay,
        loading = pending,
        onClick = onClick
    )
}


@Preview
@Composable
fun TransactionCardPreview(){
    TransactionCard(
        modifier = Modifier.width(400.dp),
        title = "TRANSACTION TITLE",
        subTitle = "TRANSACTION SUBTITLE",
        amount = 5000.0,
        pending = false,
        date = "15.10.2023",
        transactionType = "Salary",
        isPay = true,
        createdBy = "YASSER",
        currency = "$",
        onClick = {}
    )
}
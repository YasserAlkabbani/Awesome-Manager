package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmText

@Composable
fun TransactionCard(
    modifier: Modifier,
    title:String,subTitle:String,amount:Double,
    date:String,transactionType:String,isPay:Boolean,currency:String,createdBy:String,
    onClick:()->Unit
) {

    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val amountString= remember(amount) { amount.toString() }
    val cardColor= remember(isPay) { if (isPay)errorContainer else primaryContainer }

    AmCard(
        modifier=modifier,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        content = {
            Row(modifier = Modifier.fillMaxWidth()) {
                AmText(modifier = Modifier.fillMaxWidth().weight(1f),text = title, style = MaterialTheme.typography.titleLarge)
                AmText(modifier = Modifier.wrapContentWidth(),text = currency+amountString, style = MaterialTheme.typography.titleLarge)
            }
            AmText(text = subTitle)
            Row {
                AmTitleWithSubtitle(modifier = Modifier.fillMaxWidth().weight(1f),title = "Type", subtitle = transactionType)
                AmTitleWithSubtitle(modifier = Modifier.fillMaxWidth().weight(1f),title = "Creator", subtitle = createdBy)
                AmTitleWithSubtitle(modifier = Modifier.fillMaxWidth().weight(1f),title = "Data", subtitle = date)
            }
        },
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
        date = "15.10.2023",
        transactionType = "Salary",
        isPay = true,
        createdBy = "YASSER",
        currency = "$",
        onClick = {}
    )
}
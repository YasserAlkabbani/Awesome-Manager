package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun TransactionCard(
    modifier: Modifier,
    account:String,
    title: String, subTitle: String, amount: Double, pending: Boolean,
    date: String, transactionType: String, isPay: Boolean, currency: String,
    createdBy: String, onClick: () -> Unit
) {

    AmCard(
        modifier = modifier,
        content = {
            AmText(
                modifier = Modifier.fillMaxWidth(),
                text = title, style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmText(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = account, style = MaterialTheme.typography.titleMedium,
                )
                AmText(
                    modifier = Modifier.wrapContentWidth(),
                    text = "$amount $currency",
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Row {
                AmTextWithIcon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), text = transactionType,
                    amIconsType = AmIcons.Category,
                    positive = isPay
                )
                AmTextWithIcon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), text = date,
                    amIconsType = AmIcons.Date,
                    positive = isPay
                )
            }
        },
        positive = isPay,
        loading = pending,
        onClick = onClick
    )
}


@Preview
@Composable
fun TransactionCardPreview() {
    TransactionCard(
        modifier = Modifier.width(400.dp),
        account = "ACCOUNT",
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
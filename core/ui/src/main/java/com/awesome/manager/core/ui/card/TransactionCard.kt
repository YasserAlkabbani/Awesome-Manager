package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.surface.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.getColors
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmTextWithIcon
import com.awesome.manager.core.ui.R

@Composable
fun TransactionCard(
    modifier: Modifier,
    account: String,
    title: String,
    amount: String,
    isPending: Boolean,
    date: String,
    transactionType: String,
    isPay: Boolean,
    currency: String,
    onClick: () -> Unit
) {

    AmSurface(
        modifier = modifier.fillMaxWidth(),
        content = {
            Column {
                Surface(
                    contentColor = isPay.getColors().first,
                    content = {
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            AmText(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                text = stringResource(
                                    R.string.amount_with_currency,
                                    currency,
                                    amount
                                ),
                                textStyle = MaterialTheme.typography.headlineLarge,
                            )
                            AmText(
                                modifier = Modifier,
                                text = transactionType,
                                textStyle = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AmText(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        text = account,
                        textStyle = MaterialTheme.typography.titleLarge,
                    )
                    AmText(
                        modifier = Modifier.wrapContentWidth(),
                        text = date,
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        },
        isLoading = isPending,
        onClick = onClick
    )
}


@Preview
@Composable
fun TransactionCardPreview() {
    TransactionCard(
        modifier = Modifier.width(400.dp),
        account = "Account Name",
        title = "Transaction Title",
        amount = "5000.0",
        isPending = false,
        date = "15.10.2023 - 10:55:30",
        transactionType = "Incoming",
        isPay = true,
        currency = "$",
        onClick = {}
    )
}
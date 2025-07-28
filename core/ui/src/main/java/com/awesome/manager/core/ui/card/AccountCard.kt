package com.awesome.manager.core.ui.card

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.cards.AmCard
import com.awesome.manager.core.designsystem.component.surface.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.getColors


@Composable
fun AccountCardWithDetails(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    loading: Boolean,
    creditorDebtor: CardBalanceDetails.CreditorDebtor,
    incomeExpenses: CardBalanceDetails.IncomeExpenses,
    currencySymbol: String,
    lastTransactionAt: String
) {
    AmSurface(
        modifier = modifier.fillMaxWidth(),
        isLoading = loading,
        content = {
            AccountBasic(
                title = title,
                imageUrl = imageUrl,
                currencySymbol = currencySymbol,
                lastTransactionAt = lastTransactionAt
            )
            AmBalanceDetailsCard(
                creditorDebtor = creditorDebtor,
                incomeExpenses = incomeExpenses,
            )
        }
    )
}

@Composable
fun AccountCard(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    currencySymbol: String,
    lastTransactionAt: String,
    loading: Boolean,
    onClick: () -> Unit
) {
    AmSurface(
        modifier = modifier.fillMaxWidth(),
        isLoading = loading,
        onClick = onClick,
        content = {
            AccountBasic(
                title = title,
                imageUrl = imageUrl,
                currencySymbol = currencySymbol,
                lastTransactionAt = lastTransactionAt,
            )
        }
    )
}


@Composable
fun AccountBasic(
    title: String,
    imageUrl: String,
    currencySymbol: String,
    lastTransactionAt: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AmImage(modifier = Modifier.size(AmSize.LARGE.value), imageUrl = imageUrl)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AmText(text = title, textStyle = MaterialTheme.typography.titleLarge)
            Row {
                AmText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = "Currency: $currencySymbol",
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                AmText(
                    modifier = Modifier,
                    text = "Last Transaction: At $lastTransactionAt",
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview
@Composable
fun AccountCardPreview() {
    AccountCard(
        modifier = Modifier.width(400.dp),
        title = "Account Name",
        imageUrl = "",
        loading = true,
        currencySymbol = "S",
        lastTransactionAt = "$",
        onClick = {},
    )
}

@Preview
@Composable
fun AccountCardWithDetailsPreview() {
    AccountCardWithDetails(
        modifier = Modifier.width(400.dp),
        title = "Account Name",
        imageUrl = "",
        loading = true,
        creditorDebtor = CardBalanceDetails.CreditorDebtor(
            creditor = "100.0",
            debtor = "600.0",
            netDebtorAbs = "3000.0",
            isPositiveDebtor = true,
        ),
        incomeExpenses = CardBalanceDetails.IncomeExpenses(
            income = "500.0",
            expenses = "300.0",
            netIncomeAbs = "5000.0",
            isPositiveIncome = false,
        ),
        currencySymbol = "$",
        lastTransactionAt = "12-11-2025 15:08"
    )
}
package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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


@Composable
fun AccountCard(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    currencySymbol: String,
    balance: String,
    isPositiveDebtor: Boolean,
    loading: Boolean,
    onClick: () -> Unit
) {
    AmCard(
        modifier = modifier.fillMaxWidth(),
        isPositive = isPositiveDebtor,
        isLoading = loading,
        onClick = onClick,
        content = {
            AccountBasic(
                title = title,
                imageUrl = imageUrl,
                balance = balance,
                currencySymbol = currencySymbol,
            )
        }
    )
}

@Composable
fun AccountCardWithDetails(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    loading: Boolean,
    creditorDebtor: CardBalanceDetails.CreditorDebtor,
    incomeExpenses: CardBalanceDetails.IncomeExpenses,
    currencySymbol: String,
) {
    AmCard(
        modifier = modifier.fillMaxWidth(),
        isPositive = creditorDebtor.isPositiveDebtor,
        isLoading = loading,
        content = {
            AccountBasic(
                title = title,
                imageUrl = imageUrl,
                balance = creditorDebtor.balance,
                currencySymbol = currencySymbol,
            )
            AmBalanceDetailsCard(
                creditorDebtor = creditorDebtor,
                incomeExpenses = incomeExpenses,
            )
        }
    )
}


@Composable
fun AccountBasic(
    title: String,
    imageUrl: String,
    balance: String,
    currencySymbol: String,
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
            AmText(text = title, textStyle = MaterialTheme.typography.titleMedium)
            AmText(
                text = "$balance $currencySymbol",
                textStyle = MaterialTheme.typography.titleMedium
            )
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
        balance = "1000,00",
        isPositiveDebtor = true,
        currencySymbol = "$",
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
    )
}
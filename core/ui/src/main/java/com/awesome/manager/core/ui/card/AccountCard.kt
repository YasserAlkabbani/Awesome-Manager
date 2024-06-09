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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmSpacerLargeWidth
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons


@Composable
fun AccountCard(
    modifier: Modifier,
    title: String, imageUrl: String,
    loading: Boolean, withDetails: Boolean,

    creditor: Double, debtor: Double, netDebtorAbs: Double, isPositiveDebtor: Boolean,
    income: Double, expenses: Double, netIncomeAbs: Double, isPositiveIncome: Boolean,
    currencySymbol: String,

    onClick: (() -> Unit)?,
    onAddTransaction: (() -> Unit)?,
    onEditTransaction: (() -> Unit)?,
) {
    AmCard(
        modifier = modifier,
        positive = isPositiveDebtor,
        loading = loading,
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmImage(modifier = Modifier.size(AmSize.LARGE.value), imageUrl = imageUrl)
                AmSpacerLargeWidth()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    AmText(text = title, style = MaterialTheme.typography.titleMedium)
                    AmText(
                        text = "$netDebtorAbs $currencySymbol",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                onAddTransaction?.let {
                    AmIconButton(
                        modifier = Modifier,
                        amIconsType = AmIcons.TransactionAdd,
                        positive = null,
                        onClick = it
                    )
                }
                onEditTransaction?.let {
                    AmIconButton(
                        modifier = Modifier,
                        amIconsType = AmIcons.Edit,
                        positive = null,
                        onClick = it
                    )
                }
            }

            if (withDetails) {
                AmBalanceDetailsCard(
                    creditor = creditor,
                    debtor = debtor,
                    netDebtorAbs = netDebtorAbs,
                    isPositiveDebtor = isPositiveDebtor,
                    income = income,
                    expenses = expenses,
                    netIncomeAbs = netIncomeAbs,
                    isPositiveIncome = isPositiveIncome,
                    currencySymbol = currencySymbol,
                )
            }

        }
    )
}

@Preview
@Composable
fun AccountCardPreview() {
    AccountCard(
        modifier = Modifier.width(400.dp),
        title = "TITLE", imageUrl = "",
        loading = true, withDetails = true,
        onClick = {}, onAddTransaction = {}, onEditTransaction = {},
        creditor = 100.0, debtor = 600.0,
        income = 500.0, expenses = 300.0, currencySymbol = "$",
        netDebtorAbs = 3000.0, netIncomeAbs = 5000.0,
        isPositiveDebtor = true, isPositiveIncome = false,
    )
}
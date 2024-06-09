package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallWidth
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmTextWithIcon
import com.awesome.manager.core.ui.R

@Composable
fun AmBalanceDetailsCard(
    creditor: Double, debtor: Double, netDebtorAbs: Double, isPositiveDebtor: Boolean,
    income: Double, expenses: Double, netIncomeAbs: Double, isPositiveIncome: Boolean,
    currencySymbol: String,
) {
    Row(Modifier.padding(AmPadding.MEDIUM.value)) {
        BalanceDetailsRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            negativeValue = creditor,
            negativeLabel = stringResource(R.string.creditor),
            positiveValue = debtor,
            positiveLabel = stringResource(R.string.debtor),
            balance = netDebtorAbs,
            isPositiveBalance = isPositiveDebtor,
            currency = currencySymbol
        )
        AmSpacerSmallWidth()
        BalanceDetailsRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            positiveValue = income,
            positiveLabel = stringResource(R.string.income),
            negativeValue = expenses,
            negativeLabel = stringResource(R.string.expenses),
            balance = netIncomeAbs,
            isPositiveBalance = isPositiveIncome,
            currency = currencySymbol
        )
    }
}

@Composable
fun BalanceDetailsRow(
    modifier: Modifier,
    positiveValue: Double, positiveLabel: String,
    negativeValue: Double, negativeLabel: String,
    balance: Double, isPositiveBalance: Boolean,
    currency: String
) {
    Column(modifier = modifier) {
        AmText(text = "$positiveLabel/$negativeLabel")
        AmTextWithIcon(
            modifier = Modifier.fillMaxWidth(),
            text = "$positiveValue $currency",
            amIconsType = AmIcons.Input, positive = true,
        )
        AmSpacerSmallHeight()
        AmTextWithIcon(
            modifier = Modifier.fillMaxWidth(),
            text = "$negativeValue $currency",
            amIconsType = AmIcons.Output, positive = false
        )
        AmSpacerSmallHeight()
        AmTextWithIcon(
            modifier = Modifier.fillMaxWidth(),
            text = "$balance $currency",
            amIconsType = AmIcons.Balance, positive = isPositiveBalance
        )
    }
}
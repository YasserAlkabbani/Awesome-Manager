package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallWidth
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmTextWithIcon
import com.awesome.manager.core.ui.R

@Composable
fun AmBalanceDetailsCard(
    creditor: String, debtor: String, netDebtorAbs: String, isPositiveDebtor: Boolean,
    income: String, expenses: String, netIncomeAbs: String, isPositiveIncome: Boolean,
) {
    AmSurface(
        modifier = Modifier.padding(AmPadding.SMALL.value),
        padding = AmPadding.LARGE
    ) {
        Row {
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
            )
            AmSpacerSmallWidth()
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
            )
        }
    }
}

@Composable
fun BalanceDetailsRow(
    modifier: Modifier,
    positiveValue: String, positiveLabel: String,
    negativeValue: String, negativeLabel: String,
    balance: String, isPositiveBalance: Boolean,
) {
    Column(modifier = modifier) {
        AmText(
            text = "$positiveLabel/$negativeLabel",
            style = MaterialTheme.typography.titleMedium
        )
        AmCard(
            positive = true
        ) {
            AmTextWithIcon(
                modifier = Modifier.fillMaxWidth(),
                text = positiveValue,
                amIconsType = AmIcons.Input,
            )
        }
        AmSpacerSmallHeight()
        AmCard(positive = false) {
            AmTextWithIcon(
                modifier = Modifier.fillMaxWidth(),
                text = negativeValue,
                amIconsType = AmIcons.Output,
            )
        }
        AmSpacerSmallHeight()
        AmCard(positive = isPositiveBalance) {
            AmTextWithIcon(
                modifier = Modifier.fillMaxWidth(),
                text = balance,
                amIconsType = AmIcons.Balance,
            )
        }
    }
}
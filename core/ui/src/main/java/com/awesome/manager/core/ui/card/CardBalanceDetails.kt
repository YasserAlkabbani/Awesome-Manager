package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.getColors
import com.awesome.manager.core.ui.R

sealed interface CardBalanceDetails {

    val positiveValue: String
    val negativeValue: String
    val balance: String
    val isPositiveBalance: Boolean

    @Composable
    fun negativeLabel(): String

    @Composable
    fun positiveLabel(): String

    data class CreditorDebtor(
        val creditor: String,
        val debtor: String,
        val netDebtorAbs: String,
        val isPositiveDebtor: Boolean
    ) : CardBalanceDetails {


        override val positiveValue: String = creditor
        override val negativeValue: String = debtor
        override val balance: String = netDebtorAbs
        override val isPositiveBalance: Boolean = isPositiveDebtor

        @Composable
        override fun negativeLabel(): String = stringResource(R.string.income)

        @Composable
        override fun positiveLabel(): String = stringResource(R.string.expenses)

    }

    data class IncomeExpenses(
        val income: String,
        val expenses: String,
        val netIncomeAbs: String,
        val isPositiveIncome: Boolean,
    ) : CardBalanceDetails {

        override val positiveValue: String = income
        override val negativeValue: String = expenses
        override val balance: String = netIncomeAbs
        override val isPositiveBalance: Boolean = isPositiveIncome

        @Composable
        override fun negativeLabel(): String = stringResource(R.string.creditor)

        @Composable
        override fun positiveLabel(): String = stringResource(R.string.debtor)

    }
}

data class BalanceData(
    val creditorDebtor: CardBalanceDetails.CreditorDebtor,
    val incomeExpenses: CardBalanceDetails.IncomeExpenses,
    val currencyCode: String
) {
    companion object {
        fun generate(
            debtor: String,
            creditor: String,
            netDebtorAbs: String,
            isPositiveDebtor: Boolean,
            income: String,
            expenses: String,
            netIncomeAbs: String,
            isPositiveIncome: Boolean,
            currencyCode: String
        ): BalanceData = BalanceData(
            creditorDebtor = CardBalanceDetails.CreditorDebtor(
                debtor = debtor,
                creditor = creditor,
                netDebtorAbs = netDebtorAbs,
                isPositiveDebtor = isPositiveDebtor,
            ),
            incomeExpenses = CardBalanceDetails.IncomeExpenses(
                income = income,
                expenses = expenses,
                netIncomeAbs = netIncomeAbs,
                isPositiveIncome = isPositiveIncome,
            ),
            currencyCode = currencyCode
        )
    }
}

@Composable
fun AmBalanceDetailsCard(balanceData: BalanceData) {
    Row {
        BalanceDetailsRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cardBalanceDetails = balanceData.creditorDebtor,
            currencyCode = balanceData.currencyCode
        )
        BalanceDetailsRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cardBalanceDetails = balanceData.incomeExpenses,
            currencyCode = balanceData.currencyCode
        )
    }
}

@Composable
fun AmBalanceCard(balanceData: BalanceData) {
    Row {
        BalanceRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cardBalanceDetails = balanceData.creditorDebtor,
            currencyCode = balanceData.currencyCode
        )
        BalanceRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cardBalanceDetails = balanceData.incomeExpenses,
            currencyCode = balanceData.currencyCode
        )
    }
}

@Composable
private fun BalanceDetailsRow(
    modifier: Modifier,
    cardBalanceDetails: CardBalanceDetails,
    currencyCode: String
) {
    Column(modifier = modifier) {
        AmText(
            text = "${cardBalanceDetails.positiveLabel()}/${cardBalanceDetails.negativeLabel()}",
            textStyle = MaterialTheme.typography.titleMedium,
            amTextPadding = AmPadding.ZERO
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            contentColor = true.getColors().first
        ) {
            AmText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.amount_with_currency,
                    currencyCode,
                    cardBalanceDetails.positiveValue
                ),
                amTextPadding = AmPadding.ZERO,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            contentColor = false.getColors().first
        ) {
            AmText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.amount_with_currency,
                    currencyCode,
                    cardBalanceDetails.negativeValue
                ),
                amTextPadding = AmPadding.ZERO,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            contentColor = cardBalanceDetails.isPositiveBalance.getColors().first
        ) {
            AmText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.amount_with_currency,
                    currencyCode,
                    cardBalanceDetails.balance
                ),
                amTextPadding = AmPadding.ZERO,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun BalanceRow(
    modifier: Modifier,
    cardBalanceDetails: CardBalanceDetails,
    currencyCode: String
) {
    Column(modifier = modifier) {
        AmText(
            text = when (cardBalanceDetails.isPositiveBalance) {
                true -> cardBalanceDetails.positiveLabel()
                false -> cardBalanceDetails.negativeLabel()
            },
            textStyle = MaterialTheme.typography.titleMedium,
            amTextPadding = AmPadding.ZERO
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            contentColor = cardBalanceDetails.isPositiveBalance.getColors().first
        ) {
            AmText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.amount_with_currency,
                    currencyCode,
                    cardBalanceDetails.balance
                ),
                amTextPadding = AmPadding.ZERO,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AmBalanceDetailsCardPreview() {
    AmBalanceDetailsCard(
        balanceData = BalanceData.generate(
            creditor = "1000.0",
            debtor = "200.0",
            netDebtorAbs = "500.0",
            isPositiveDebtor = true,
            income = "200",
            expenses = "3400.0",
            netIncomeAbs = "100",
            isPositiveIncome = true,
            currencyCode = "USD"
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun AmBalanceCardPreview() {
    AmBalanceCard(
        balanceData = BalanceData.generate(
            creditor = "1000.0",
            debtor = "200.0",
            netDebtorAbs = "500.0",
            isPositiveDebtor = true,
            income = "200",
            expenses = "3400.0",
            netIncomeAbs = "100",
            isPositiveIncome = true,
            currencyCode = "USD"
        ),
    )
}
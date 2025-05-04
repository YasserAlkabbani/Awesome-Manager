package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmSpacerMediumWidth
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.surface.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmTextWithIcon
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
        val income:String,
        val expenses:String,
        val netIncomeAbs:String,
        val isPositiveIncome:Boolean,
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

@Composable
fun AmBalanceDetailsCard(
    creditorDebtor: CardBalanceDetails.CreditorDebtor,
    incomeExpenses: CardBalanceDetails.IncomeExpenses,
) {
    Row {
        BalanceDetailsRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cardBalanceDetails = creditorDebtor
        )
        AmSpacerMediumWidth()
        BalanceDetailsRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cardBalanceDetails = incomeExpenses,
        )
    }
}

@Composable
private fun BalanceDetailsRow(
    modifier: Modifier,
    cardBalanceDetails: CardBalanceDetails
) {
    Column(modifier = modifier) {
        AmText(
            text = "${cardBalanceDetails.positiveLabel()}/${cardBalanceDetails.negativeLabel()}",
            textStyle = MaterialTheme.typography.titleMedium
        )
        AmSurface(
            modifier=Modifier.fillMaxWidth(),
            isPositive = true
        ) {
            AmTextWithIcon(
                modifier = Modifier.fillMaxWidth(),
                text = cardBalanceDetails.positiveValue,
                amIconsType = AmIcons.Input,
            )
        }
        AmSpacerSmallHeight()
        AmSurface(
            modifier=Modifier.fillMaxWidth(),
            isPositive = false
        ) {
            AmTextWithIcon(
                modifier = Modifier.fillMaxWidth(),
                text = cardBalanceDetails.negativeValue,
                amIconsType = AmIcons.Output,
            )
        }
        AmSpacerSmallHeight()
        AmSurface(
            modifier=Modifier.fillMaxWidth(),
            isPositive = cardBalanceDetails.isPositiveBalance
        ) {
            AmTextWithIcon(
                modifier = Modifier.fillMaxWidth(),
                text = cardBalanceDetails.balance,
                amIconsType = AmIcons.Balance,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AmBalanceDetailsCardPreview() {
    AmBalanceDetailsCard(
        creditorDebtor = CardBalanceDetails.CreditorDebtor(
            creditor = "1000.0",
            debtor = "200.0",
            netDebtorAbs = "500.0",
            isPositiveDebtor = true
        ),
        incomeExpenses = CardBalanceDetails.IncomeExpenses(
            income = "200",
            expenses = "3400.0",
            netIncomeAbs = "100",
            isPositiveIncome = true
        )
    )
}
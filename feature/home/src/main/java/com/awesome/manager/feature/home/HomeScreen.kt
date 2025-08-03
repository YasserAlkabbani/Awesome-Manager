package com.awesome.manager.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9_PRO_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmLinearProgressIndicator
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.surface.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.model.AmAccountWithBalance
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.card.AmBalanceDetailsCard
import com.awesome.manager.core.model.BalanceDetails
import com.awesome.manager.core.ui.NoDataWarning
import com.awesome.manager.core.ui.card.BalanceData
import com.awesome.manager.core.ui.card.CardBalanceDetails
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_HOME
import timber.log.Timber

@Composable
internal fun HomeScreenRoute(
    navigateToCreateAccount: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val currencies: AmState<List<AmCurrency>> =
        homeViewModel.currencies.collectAsStateWithLifecycle().value
    val accounts: AmState<List<AmAccountWithBalance>> =
        homeViewModel.accounts.collectAsStateWithLifecycle().value
    val transactions: AmState<List<AmTransaction>> =
        homeViewModel.transactions.collectAsStateWithLifecycle().value

    HomeScreen(
//        balanceDetailsState = balanceState,
        currencies = currencies,
        accounts = accounts,
        transactions = transactions,
        navigateToCreateAccount = navigateToCreateAccount
    )
}


@Composable
internal fun HomeScreen(
//    balanceDetailsState: AmState<List<BalanceDetails>>,
    currencies: AmState<List<AmCurrency>>,
    accounts: AmState<List<AmAccountWithBalance>>,
    transactions: AmState<List<AmTransaction>>,
    navigateToCreateAccount: () -> Unit
) {

    Timber.d("TEST_AM $currencies $accounts $transactions")

    Column(Modifier.fillMaxSize()) {
        LoadingDataState(currencies)
        LoadingDataState(accounts)
        LoadingDataState(transactions)

        /*AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = balanceDetailsState,
            contentAlignment = Alignment.TopCenter
        ) { balanceDetailsState ->
            when (balanceDetailsState) {
                is AmState.Success -> {
                    val balanceDetails = balanceDetailsState.data
                    when (balanceDetails.isEmpty()) {
                        true -> NoDataWarning(
                            title = stringResource(R.string.theres_no_accounts_yet),
                            buttonText = stringResource(R.string.create_an_account),
                            onClickButton = navigateToCreateAccount
                        )

                        false -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(AmPadding.SMALL.value),
                            contentPadding = PaddingValues(
                                bottom = AmPadding.XX_LARGE.value
                            ),
                            content = {
                                items(
                                    items = balanceDetails,
                                    contentType = { LAZY_ITEM_HOME },
                                    key = { it.currency.id },
                                    itemContent = { balanceDetails ->
                                        HomeCard(
                                            incomeExpenses = CardBalanceDetails.IncomeExpenses(
                                                income = balanceDetails.formattedIncome,
                                                expenses = balanceDetails.formattedExpenses,
                                                netIncomeAbs = balanceDetails.formattedNetIncome,
                                                isPositiveIncome = balanceDetails.isPositiveIncome,
                                            ),
                                            creditorDebtor = CardBalanceDetails.CreditorDebtor(
                                                creditor = balanceDetails.formattedCreditor,
                                                debtor = balanceDetails.formattedDebtor,
                                                netDebtorAbs = balanceDetails.formattedNetDebtor,
                                                isPositiveDebtor = balanceDetails.isPositiveDebtor,
                                            ),
                                            netCash = balanceDetails.formattedNetCash,
                                            isPositiveCash = balanceDetails.isPositiveCash,
                                            currencyCode = balanceDetails.currency.currencyCode,
                                            currencySymbol = balanceDetails.currency.currencySymbol,
                                        )
                                    }
                                )
                            })
                    }
                }

                is AmState.Error -> NoDataWarning(
                    title = stringResource(R.string.theres_no_accounts_yet),
                    buttonText = stringResource(R.string.create_an_account),
                    onClickButton = navigateToCreateAccount
                )

                is AmState.Loading -> {}
            }
        }*/
    }

}

@Composable
fun LoadingDataState(amState: AmState<Any>) {
    AnimatedContent(amState) { amState ->
        when (amState) {
            is AmState.Error -> AmButton(
                text = "Retry",
                enabled = false,
                amIconsType = AmIcons.Retry,
                onClick = {}
            )

            is AmState.Loading -> Row {
                AmText(text = "Loading")
                AmLinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is AmState.Success<*> -> Unit
        }
    }
}

@Composable
fun HomeCard(
    balanceData: BalanceData,
    netCash: String,
    currencyCode: String,
) {
    AmSurface(
        modifier = Modifier.fillMaxWidth(),
        padding = AmPadding.CARD_PADDING_MEDIUM
    ) {
        AmSurface(
            modifier = Modifier.fillMaxWidth(),
            padding = AmPadding.CARD_PADDING_MEDIUM
        ) {
            Row(
                modifier = Modifier
                    .padding(AmPadding.SMALL.value)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AmText(
                    text = "Cash (${currencyCode})",
                    textStyle = MaterialTheme.typography.titleLarge
                )
                AmText(
                    text = "$netCash ${balanceData.currencyCode}",
                    textStyle = MaterialTheme.typography.titleLarge
                )


            }
        }
        AmBalanceDetailsCard(
            balanceData = balanceData
        )

    }
}

@Preview(device = PIXEL_9_PRO_XL, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
//        balanceDetailsState = AmState.Success(
//            listOf(
//                BalanceDetails.createDemo(),
//                BalanceDetails.createDemo(),
//                BalanceDetails.createDemo()
//            )
//        ),
        currencies = AmState.Loading(),
        accounts = AmState.Error(amUIError = AmUIError.NoDataError),
        transactions = AmState.Success(listOf()),
        navigateToCreateAccount={}
    )
}


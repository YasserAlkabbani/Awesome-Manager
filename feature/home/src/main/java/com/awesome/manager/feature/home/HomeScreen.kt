package com.awesome.manager.feature.home

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.data.states.DataState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.ui.card.AmBalanceDetailsCard
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_HOME
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeRoute(
    sendMainAction: (MainAction) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState = homeViewModel.homeState

    val mainAction = homeState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, homeState::doneMainAction)
    }
    HomeScreen(homeState)
}


@Composable
fun HomeScreen(homeState: HomeState) {

    when (val currencyWithBalance = homeState.balanceDetails.collectAsState().value) {
        is DataState.Success -> LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AmPadding.SMALL.value),
            contentPadding = PaddingValues(
                start = AmPadding.SMALL.value,
                end = AmPadding.SMALL.value,
                bottom = AmPadding.XX_LARGE.value
            ),
            content = {
                items(
                    items = currencyWithBalance.data,
                    contentType = { LAZY_ITEM_HOME },
                    key = { it.currency.id },
                    itemContent = { balanceDetails ->
                        HomeCard(
                            creditor = balanceDetails.formattedCreditor,
                            debtor = balanceDetails.formattedDebtor,
                            netDebtorAbs = balanceDetails.formattedNetDebtor,
                            isPositiveDebtor = balanceDetails.isPositiveDebtor,
                            income = balanceDetails.formattedIncome,
                            expenses = balanceDetails.formattedExpenses,
                            netIncomeAbs = balanceDetails.formattedNetIncome,
                            isPositiveIncome = balanceDetails.isPositiveIncome,
                            netCash = balanceDetails.formattedNetCash,
                            isPositiveCash = balanceDetails.isPositiveCash,
                            currencyCode = balanceDetails.currency.currencyCode,
                            currencySymbol = balanceDetails.currency.currencySymbol,
                        )
                    }
                )
            })

        DataState.Error -> {
            Column(
                modifier = Modifier
                    .padding(AmPadding.XX_LARGE.value)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AmText(
                    text = stringResource(R.string.theres_no_accounts_yet),
                    maxLines = 3, textAlign = TextAlign.Center
                )
                AmFilledTonalButton(
                    text = stringResource(R.string.create_an_account),
                    onClick = homeState::navigateToCreateAccount,
                )
            }
        }

        DataState.Loading -> {}
    }
}

@Composable
fun HomeCard(
    creditor: String, debtor: String, netDebtorAbs: String, isPositiveDebtor: Boolean,
    income: String, expenses: String, netIncomeAbs: String, isPositiveIncome: Boolean,
    netCash: String, isPositiveCash: Boolean, currencyCode: String, currencySymbol: String,
) {
    AmCard(
        modifier = Modifier.fillMaxWidth(),
        positive = isPositiveCash,
        padding = AmPadding.ZERO
    ) {
        AmSurface(
            modifier = Modifier.fillMaxWidth(),
            positive = isPositiveCash,
            padding = AmPadding.MEDIUM
        ) {
            Row(
                modifier = Modifier
                    .padding(AmPadding.MEDIUM.value)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AmText(
                    text = "Cash (${currencyCode})",
                    style = MaterialTheme.typography.titleLarge
                )
                AmText(
                    text = "$netCash $currencySymbol",
                    style = MaterialTheme.typography.titleLarge
                )


            }
        }
        AmBalanceDetailsCard(
            creditor = creditor,
            debtor = debtor,
            netDebtorAbs = netDebtorAbs,
            isPositiveDebtor = isPositiveDebtor,
            income = income,
            expenses = expenses,
            netIncomeAbs = netIncomeAbs,
            isPositiveIncome = isPositiveIncome,
        )

    }
}

@Preview(device = PIXEL_4_XL)
@Composable
fun HomeScreenPreview() {
    HomeScreen(HomeState(balanceDetails = MutableStateFlow(DataState.Success(listOf()))))
}


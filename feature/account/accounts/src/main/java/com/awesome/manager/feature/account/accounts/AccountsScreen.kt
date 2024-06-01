package com.awesome.manager.feature.account.accounts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.ui_actions.navigation.sendMainAction
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT

@Composable
fun AccountsRoute(
    sendMainAction: (MainAction) -> Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
) {

    val accountsState = accountsViewModel.accountsState

    val navigationAction = accountsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, accountsState::resetNavigationAction)
    })

    val appBarAction = accountsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, accountsState::resetAppBar)
    })

    val bottomSheetAction = accountsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, accountsState::idleBottomSheet)
    })

    AccountsScreen(accountsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsScreen(
    accountsState: AccountsState
) {
    val accountsLazyPaging = accountsState.accounts.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AmLazyColumn(
            content = {
                if (accountsLazyPaging.itemCount == 0)
                    item {
                        Column(
                            modifier = Modifier
                                .padding(AmPadding.EXTRA_LARGE.value)
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
                                onClick = accountsState::navigateToCreateAccount,
                                positive = null
                            )
                        }
                    }
                else items(
                    count = accountsLazyPaging.itemCount,
                    contentType = { LAZY_ITEM_ACCOUNT },
                    key = accountsLazyPaging.itemKey { it.id },
                    itemContent = { index ->
                        accountsLazyPaging[index]?.let { account ->
                            val balanceDetails = account.balanceDetails
                            AccountCard(
                                modifier = Modifier.animateItemPlacement(),
                                title = account.name,
                                imageUrl = account.imageUrl,
                                loading = account.pending,
                                withDetails = false,
                                onClick = { accountsState.navigateToAccountDetails(account.id) },
                                onAddTransaction = {
                                    accountsState.navigateToCreateTransaction(account.id)
                                },
                                onEditTransaction = null,
                                income = balanceDetails.income,
                                expenses = balanceDetails.expenses,
                                netIncomeAbs = balanceDetails.netIncomeAbs,
                                debtor = balanceDetails.debtor,
                                creditor = balanceDetails.creditor,
                                netDebtorAbs = balanceDetails.netDebtorAbs,
                                currencySymbol = balanceDetails.currency.currencySymbol,
                                isPositiveIncome = balanceDetails.isPositiveIncome,
                                isPositiveDebtor = balanceDetails.isPositiveDebtor
                            )
                        }
                    }
                )
            }
        )

    }
}
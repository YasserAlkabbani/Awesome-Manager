package com.awesome.manager.feature.account.accounts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.component.chips.AmFilterChip
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT

@Composable
fun AccountsRoute(
    sendMainAction: (MainAction) -> Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
) {

    val accountsState = accountsViewModel.accountsState

    val mainAction = accountsState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, accountsState::doneMainAction)
    }

    AccountsScreen(accountsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsScreen(
    accountsState: AccountsState
) {
    val isLoading = accountsState.isLoading.collectAsState().value
    val accountsLazyPaging = accountsState.accounts.collectAsLazyPagingItems()
    val filterData by accountsState.filterData.collectAsState()
    val noItems = remember {
        derivedStateOf {
            !filterData.filterApplauded && accountsLazyPaging.itemCount == 0
        }
    }.value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        if (noItems)
            Column(
                modifier = Modifier
                    .padding(AmPadding.X_LARGE.value)
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
                )
            }
        else {
            AmLazyColumn(
                isRefreshing = isLoading,
                onRefresh = accountsState.refreshAccounts,
                content = {

                    item(contentType = "FILTER", key = "FILTER") {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                val searchLabel = stringResource(id = R.string.search_in_accounts)
                                AmFilterChip(
                                    selected = filterData.searchFilter,
                                    label = stringResource(R.string.search),
                                    value = filterData.searchKey,
                                    amIconsType = AmIcons.Search,
                                    onClick = {
                                        accountsState.showSearchWithContentBottomSheet(
                                            searchLabel = searchLabel,
                                            initSearch = filterData.searchKey.orEmpty(),
                                            onReSearch = accountsState::updateSearchKey,
                                            onSearchDone = accountsState::dismissBottomSheet,
                                            content = {}
                                        )
                                    },
                                    onRemove = accountsState::clearSearch
                                )
                            }
                        }
                    }
                    items(
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
                                    income = balanceDetails.formattedIncome,
                                    expenses = balanceDetails.formattedExpenses,
                                    netIncomeAbs = balanceDetails.formattedNetIncome,
                                    debtor = balanceDetails.formattedDebtor,
                                    creditor = balanceDetails.formattedCreditor,
                                    netDebtorAbs = balanceDetails.formattedNetDebtor,
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
}
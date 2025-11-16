package com.awesome.manager.feature.account.accounts

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.card.BalanceData
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun AccountsRoute(
    accountsViewModel: AccountsViewModel = hiltViewModel(),
    navigateToCreateAccount: () -> Unit,
    navigateToCreateTransaction: (String) -> Unit,
    navigateToAccountDetails: (String) -> Unit,
) {

    val accountsUIStates =
        accountsViewModel.accountsUIStates.collectAsStateWithLifecycle().value

    val accountsEvent =
        accountsViewModel.accountsEvent.collectAsStateWithLifecycle().value
    LaunchedEffect(accountsEvent) {
        when (accountsEvent) {
            AccountsEvents.Idle -> Unit
            AccountsEvents.CreateAccountNavigation -> navigateToCreateAccount()
            is AccountsEvents.CreateTransactionNavigation ->
                navigateToCreateTransaction(accountsEvent.account.accountID)
            is AccountsEvents.AccountDetailsNavigation ->
                navigateToAccountDetails(accountsEvent.account.accountID)
        }
        if (accountsEvent !is AccountsEvents.Idle) accountsViewModel.doneAccountsEvents()

    }

    AccountsScreen(
        accountsUIStates = accountsUIStates,
        pagingAccounts = accountsViewModel.pagingAccounts,
        refreshAccounts = accountsViewModel::refreshAccounts,
        navigateToCreateAccount = accountsViewModel::navigateToCreateAccount,
        navigateToCreateTransaction = accountsViewModel::navigateToCreateTransaction,
        navigateToAccountDetails = accountsViewModel::navigateToAccountDetails
    )
}


@Composable
internal fun AccountsScreen(
    accountsUIStates: UIStates,
    pagingAccounts: Flow<PagingData<AmAccountWithDetails>>,
    refreshAccounts: () -> Unit,
    navigateToCreateAccount: () -> Unit,
    navigateToCreateTransaction: (AmAccount) -> Unit,
    navigateToAccountDetails: (AmAccount) -> Unit,
) {
    val accountsLazyPaging = pagingAccounts.collectAsLazyPagingItems()
    val isEmptyList = remember(accountsLazyPaging.itemCount) { accountsLazyPaging.itemCount == 0 }

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = isEmptyList,
        label = "ACCOUNTS",
        contentAlignment = Alignment.TopCenter
    ) {
        when (it) {
            true -> {
                Column(
                    modifier = Modifier
                        .padding(AmPadding.XX_LARGE.value)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AmText(
                        text = stringResource(R.string.theres_no_accounts_yet),
                        maxLines = 3,
                        textAlign = TextAlign.Center
                    )
                    AmFilledTonalButton(
                        text = stringResource(R.string.create_an_account),
                        onClick = navigateToCreateAccount,
                    )
                }
            }

            false -> {
                AmLazyColumn(
                    isRefreshing = accountsUIStates == UIStates.Loading,
                    onRefresh = refreshAccounts,
                    content = {
                        items(
                            count = accountsLazyPaging.itemCount,
                            contentType = { LAZY_ITEM_ACCOUNT },
                            key = accountsLazyPaging.itemKey { it.account.accountID },
                            itemContent = { index ->
                                accountsLazyPaging[index]?.let { accountWithDetails ->
                                    val balanceDetails = accountWithDetails
                                    val account = accountWithDetails.account
                                    AccountCard(
                                        modifier = Modifier,
                                        title = account.name,
                                        imageUrl = account.imageUrl,
                                        loading = account.pending,
                                        onClick = { navigateToAccountDetails(account) },
                                        balanceData = BalanceData.generate(
                                            debtor = balanceDetails.formattedDebtor,
                                            creditor = balanceDetails.formattedCreditor,
                                            netDebtorAbs = balanceDetails.formattedNetDebtor,
                                            isPositiveDebtor = balanceDetails.isPositiveDebtor,
                                            income = balanceDetails.formattedIncome,
                                            expenses = balanceDetails.formattedExpenses,
                                            netIncomeAbs = balanceDetails.formattedNetIncome,
                                            isPositiveIncome = balanceDetails.isPositiveIncome,
                                            currencySymbol = balanceDetails.currencySymbol,
                                        ),
                                    )
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun AccountsScreenPreview() {
    val accountsList = buildList {
        repeat(20) {
            add(AmAccountWithDetails.createDemo(it))
        }
    }
    AccountsScreen(
        accountsUIStates = UIStates.Loading,
        pagingAccounts = flowOf(),
        refreshAccounts = {},
        navigateToCreateAccount = { },
        navigateToCreateTransaction = { },
        navigateToAccountDetails = { },
    )
}
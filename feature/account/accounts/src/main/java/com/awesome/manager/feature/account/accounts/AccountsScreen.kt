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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.model.AmAccountWithBalance
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
    navigateToAccount: (String) -> Unit,
) {

    val accountsState =
        accountsViewModel.accountsState.collectAsStateWithLifecycle().value
    val accountNavigation =
        accountsViewModel.accountNavigation.collectAsStateWithLifecycle().value

    LaunchedEffect(accountNavigation) {
        accountNavigation?.let {
            accountsViewModel.doneNavigation()
            when (accountNavigation) {
                AccountsNavigation.CreateAccount -> navigateToCreateAccount()
                is AccountsNavigation.CreateTransaction -> navigateToCreateTransaction(
                    accountNavigation.accountID
                )

                is AccountsNavigation.Account -> navigateToAccount(accountNavigation.accountID)
            }
        }
    }

    AccountsScreen(
        accountsState = accountsState,
        pagingAccounts = accountsViewModel.pagingAccounts,
        refreshAccounts = accountsViewModel::refreshAccounts,
        navigateToCreateAccount = { accountsViewModel.navigateTo(AccountsNavigation.CreateAccount) },
        navigateToCreateTransaction = {
            accountsViewModel.navigateTo(
                AccountsNavigation.CreateTransaction(
                    it
                )
            )
        },
        navigateToAccount = { accountsViewModel.navigateTo(AccountsNavigation.Account(it)) }
    )
}


@Composable
internal fun AccountsScreen(
    accountsState: AccountsState,
    pagingAccounts: Flow<PagingData<AmAccountWithBalance>>,
    refreshAccounts: () -> Unit,
    navigateToCreateAccount: () -> Unit,
    navigateToCreateTransaction: (String) -> Unit,
    navigateToAccount: (String) -> Unit,
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
                    isRefreshing = accountsState == AccountsState.LOADING,
                    onRefresh = refreshAccounts,
                    content = {
                        items(
                            count = accountsLazyPaging.itemCount,
                            contentType = { LAZY_ITEM_ACCOUNT },
                            key = accountsLazyPaging.itemKey { it.account.accountID },
                            itemContent = { index ->
                                accountsLazyPaging[index]?.let { accountWithBalance ->
                                    val balanceDetails = accountWithBalance.balanceDetails
                                    val account = accountWithBalance.account
                                    AccountCard(
                                        modifier = Modifier,
                                        title = account.name,
                                        imageUrl = account.imageUrl,
                                        loading = account.pending,
                                        onClick = { navigateToAccount(account.accountID) },
                                        balanceData = BalanceData.generate(
                                            debtor = balanceDetails.formattedDebtor,
                                            creditor = balanceDetails.formattedCreditor,
                                            netDebtorAbs = balanceDetails.formattedNetDebtor,
                                            isPositiveDebtor = balanceDetails.isPositiveDebtor,
                                            income = balanceDetails.formattedIncome,
                                            expenses = balanceDetails.formattedExpenses,
                                            netIncomeAbs = balanceDetails.formattedNetIncome,
                                            isPositiveIncome = balanceDetails.isPositiveIncome,
                                            currencyCode = balanceDetails.currency.currencyCode,
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
            add(AmAccountWithBalance.createDemo(it))
        }
    }
    AccountsScreen(
        accountsState = AccountsState.IDLE,
        pagingAccounts = flowOf(),
        refreshAccounts = {},
        navigateToCreateAccount = { },
        navigateToCreateTransaction = { },
        navigateToAccount = { },
    )
}
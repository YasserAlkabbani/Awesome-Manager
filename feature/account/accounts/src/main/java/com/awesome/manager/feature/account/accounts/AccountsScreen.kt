package com.awesome.manager.feature.account.accounts

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun AccountsRoute(
    sendMainAction: (MainAction) -> Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel(),
) {

    val accountsState = accountsViewModel.accountsState

    val mainAction = accountsState.mainAction.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, accountsState::doneMainAction)
    }

    AccountsScreen(accountsState)
}


@Composable
fun AccountsScreen(
    accountsState: AccountsState,
) {
    val accountsLazyPaging =
        accountsState.pagingAccounts.collectAsLazyPagingItems()
    val isEmptyList =
        remember(accountsLazyPaging.itemCount) { accountsLazyPaging.itemCount == 0 }
    val isRefreshing =
        accountsState.refreshing.collectAsStateWithLifecycle().value

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
                        onClick = accountsState::navigateToCreateAccount,
                    )
                }
            }

            false -> {
                AmLazyColumn(
                    isRefreshing = isRefreshing,
                    onRefresh = accountsState.refreshAccounts,
                    content = {
                        items(
                            count = accountsLazyPaging.itemCount,
                            contentType = { LAZY_ITEM_ACCOUNT },
                            key = accountsLazyPaging.itemKey { it.id },
                            itemContent = { index ->
                                accountsLazyPaging[index]?.let { account ->
                                    val balanceDetails = account.balanceDetails
                                    AccountCard(
                                        modifier = Modifier.animateItem(),
                                        title = account.name,
                                        imageUrl = account.imageUrl,
                                        loading = account.pending,
                                        withDetails = false,
                                        onClick = {
                                            accountsState.navigateToAccountDetails(
                                                account.id
                                            )
                                        },
                                        onAddTransaction = null,
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
}

@PreviewLightDark
@Composable
fun AccountsScreenPreview() {
    val accountsList = buildList {
        repeat(20) {
            add(AmAccount.createDemo(it))
        }
    }
    val accountState = AccountsState(
        {},
        { MutableStateFlow("") },
        refreshAccounts = {},
        pagingAccounts = flowOf(PagingData.from(accountsList))
    )
    AccountsScreen(accountState)
}
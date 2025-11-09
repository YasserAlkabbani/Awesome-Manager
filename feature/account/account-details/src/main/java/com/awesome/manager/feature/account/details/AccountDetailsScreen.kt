package com.awesome.manager.feature.account.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AMHorizontalFloatingToolbar
import com.awesome.manager.core.designsystem.component.FloatingToolBarState
import com.awesome.manager.core.designsystem.component.FloatingToolbarComponent.*
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.core.designsystem.text.asString
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.ui.card.AccountCardWithDetails
import com.awesome.manager.core.ui.card.BalanceData
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION

@Composable
internal fun AccountDetails(
    navigateToCreateTransaction: (accountID: String) -> Unit,
    navigateToEditAccount: (accountID: String) -> Unit,
    navigateBack: () -> Unit,
    accountDetailsViewModel: AccountDetailsViewModel = hiltViewModel(),
) {

    val accountDetailsState: AccountDetailsState =
        accountDetailsViewModel.accountDetailsState.collectAsStateWithLifecycle().value

    val accountTransactionsState: AccountTransactionsState =
        accountDetailsViewModel.accountTransactionsState.collectAsStateWithLifecycle().value

    val accountTransactionsPaging: LazyPagingItems<AmTransactionWithDetails> =
        accountDetailsViewModel.accountTransactionsPaging.collectAsLazyPagingItems()

    val navigation: AccountDetailsNavigation? =
        accountDetailsViewModel.navigation.collectAsStateWithLifecycle().value
    LaunchedEffect(navigation) {
        navigation?.let {
            accountDetailsViewModel.navigationDone()
            when (navigation) {
                is AccountDetailsNavigation.CreateTransaction -> navigateToCreateTransaction(navigation.accountID)
                is AccountDetailsNavigation.EditAccount -> navigateToEditAccount(navigation.accountID)
                AccountDetailsNavigation.Popup -> navigateBack()
            }
        }
    }

    AccountDetailsScreen(
        accountDetailsState = accountDetailsState,
        accountTransactionsState = accountTransactionsState,
        accountTransactionsPaging = accountTransactionsPaging,
        refresh = accountDetailsViewModel::refreshTransactions,
        navigateToCreateTransaction = accountDetailsViewModel::navigateToCreateTransaction,
        navigateToEditAccount = accountDetailsViewModel::navigateToEditAccount,
        navigateBack = accountDetailsViewModel::navigateToPopup,
    )
}

@Composable
internal fun AccountDetailsScreen(
    accountDetailsState: AccountDetailsState,
    accountTransactionsState: AccountTransactionsState,
    accountTransactionsPaging: LazyPagingItems<AmTransactionWithDetails>,
    refresh: () -> Unit,
    navigateToCreateTransaction: () -> Unit,
    navigateToEditAccount: () -> Unit,
    navigateBack: () -> Unit
) {

    Box {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
            targetState = accountDetailsState,
            label = "ACCOUNT_DETAILS"
        ) { accountDetailsState ->
            when (accountDetailsState) {
                is AccountDetailsState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AmPadding.Details.value),
                    ) {
                        val accountWithDetails = accountDetailsState.amAccountWithDetails
                        val account = accountWithDetails.account
                        AccountCardWithDetails(
                            modifier = Modifier,
                            title = account.name,
                            imageUrl = account.imageUrl,
                            loading = account.pending,
                            balanceData = BalanceData.generate(
                                debtor = accountWithDetails.formattedDebtor,
                                creditor = accountWithDetails.formattedCreditor,
                                netDebtorAbs = accountWithDetails.formattedNetDebtor,
                                isPositiveDebtor = accountWithDetails.isPositiveDebtor,
                                income = accountWithDetails.formattedIncome,
                                expenses = accountWithDetails.formattedExpenses,
                                netIncomeAbs = accountWithDetails.formattedNetIncome,
                                isPositiveIncome = accountWithDetails.isPositiveIncome,
                                currencySymbol = accountWithDetails.currencySymbol,
                            )
                        )
                        AmLazyColumn(
                            isRefreshing = accountTransactionsState is AccountTransactionsState.Loading,
                            onRefresh = refresh,
                            content = {
                                items(
                                    count = accountTransactionsPaging.itemCount,
                                    contentType = { LAZY_ITEM_TRANSACTION },
                                    key = accountTransactionsPaging.itemKey { transaction -> transaction.transaction.transactionID },
                                    itemContent = { index ->
                                        accountTransactionsPaging[index]?.let { transaction ->
                                            TransactionCard(
                                                modifier = Modifier.animateItem(),
                                                account = transaction.accountName,
                                                title = transaction.transaction.title,
                                                amount = transaction.transaction.formattedAmount,
                                                isPending = transaction.transaction.pending,
                                                date = transaction.transaction.transactionAtDate,
                                                transactionType = transaction.transactionType.asString(),
                                                isPay = transaction.isPositive,
                                                currency = transaction.currencySymbol,
                                                onClick = { navigateToCreateTransaction() }
                                            )
                                        }
                                    }
                                )
                            }
                        )
                    }
                }

                is AccountDetailsState.Error -> Unit
                is AccountDetailsState.Loading -> Unit
            }
        }

        val accountDetailsFloatingToolbar = rememberAccountDetailsFloatingToolbar(
            accountDetailsState = accountDetailsState,
            popup = navigateBack,
            editAccount = navigateToEditAccount,
            createTransaction = navigateToCreateTransaction,
        )
        AMHorizontalFloatingToolbar(accountDetailsFloatingToolbar)
    }


}


@Composable
private fun rememberAccountDetailsFloatingToolbar(
    accountDetailsState: AccountDetailsState,
    popup: () -> Unit,
    editAccount: () -> Unit,
    createTransaction: () -> Unit,
): FloatingToolBarState =
    remember(accountDetailsState) {
        when (accountDetailsState) {
            AccountDetailsState.Error -> FloatingToolBarState.Error(
                errorMessage = R.string.something_wrong.asAmText(),
                backButton = IconButton(
                    amIconsType = AmIcons.ArrowBack,
                    onClick = popup
                )
            )

            AccountDetailsState.Loading -> FloatingToolBarState.Loading
            is AccountDetailsState.Success -> FloatingToolBarState.Content(
                backButton = IconButton(
                    amIconsType = AmIcons.ArrowBack,
                    onClick = popup
                ),
                editButton = IconButton(
                    amIconsType = AmIcons.Edit,
                    onClick = editAccount
                ),
                textMessage = null,//accountDetailsState.amAccountWithBalance.account.name.asAmText(),
                actionButton = ActionButton(
                    text = R.string.add.asAmText(),
                    amIconsType = AmIcons.TransactionAdd,
                    onClick = createTransaction
                )
            )
        }
    }
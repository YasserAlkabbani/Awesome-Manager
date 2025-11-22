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
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AMHorizontalFloatingToolbar
import com.awesome.manager.core.designsystem.component.FloatingToolBarState
import com.awesome.manager.core.designsystem.component.FloatingToolbarComponent.*
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.core.designsystem.text.asString
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.ui.card.AccountCardWithDetails
import com.awesome.manager.core.ui.card.BalanceData
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION

@Composable
internal fun AccountDetailsRoute(
    navigateToCreateTransaction: (accountID: String) -> Unit,
    navigateToEditAccount: (accountID: String) -> Unit,
    navigateBack: () -> Unit,
    accountDetailsViewModel: AccountDetailsViewModel = hiltViewModel(),
) {

    val accountWithDetails =
        accountDetailsViewModel.accountWithDetails.collectAsStateWithLifecycle().value

    val accountUIState: UIStates =
        accountDetailsViewModel.accountDetailsUIState.collectAsStateWithLifecycle().value

    val transactionsUIState: UIStates =
        accountDetailsViewModel.transactionsUIState.collectAsStateWithLifecycle().value

    val accountTransactionsPaging: LazyPagingItems<AmTransactionWithDetails> =
        accountDetailsViewModel.accountTransactionsPaging.collectAsLazyPagingItems()

    val accountDetailsEvent: AccountDetailsEvent =
        accountDetailsViewModel.accountDetailsEvent.collectAsStateWithLifecycle().value
    LaunchedEffect(accountDetailsEvent) {

        accountDetailsViewModel.doneAccountDetailsEvent()
        when (accountDetailsEvent) {
            AccountDetailsEvent.Idle -> Unit
            is AccountDetailsEvent.CreateTransactionNavigation ->
                navigateToCreateTransaction(accountDetailsEvent.account.accountID)

            is AccountDetailsEvent.EditAccountNavigation ->
                navigateToEditAccount(accountDetailsEvent.account.accountID)

            AccountDetailsEvent.PopupNavigation -> navigateBack()
        }
        if (accountDetailsEvent !is AccountDetailsEvent.Idle) accountDetailsViewModel.doneAccountDetailsEvent()
    }

    AccountDetailsScreen(
        accountWithDetails = accountWithDetails,
        accountUIState = accountUIState,
        transactionsUIState = transactionsUIState,
        accountTransactionsPaging = accountTransactionsPaging,
        refresh = accountDetailsViewModel::refreshTransactions,
        navigateToCreateTransaction = accountDetailsViewModel::navigateToCreateTransaction,
        navigateToEditAccount = accountDetailsViewModel::navigateToEditAccount,
        navigateBack = accountDetailsViewModel::navigateBack,
    )
}

@Composable
internal fun AccountDetailsScreen(
    accountWithDetails: AmAccountWithDetails?,
    accountUIState: UIStates,
    transactionsUIState: UIStates,
    accountTransactionsPaging: LazyPagingItems<AmTransactionWithDetails>,
    refresh: () -> Unit,
    navigateToCreateTransaction: (AmAccount) -> Unit,
    navigateToEditAccount: (AmAccount) -> Unit,
    navigateBack: () -> Unit
) {

    Box {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
            targetState = accountWithDetails,
            label = "ACCOUNT_DETAILS"
        ) { accountWithDetails ->
            when (accountWithDetails) {
                null -> Unit
                else -> Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AmPadding.Details.value),
                ) {
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
                        isRefreshing = transactionsUIState is UIStates.Loading,
                        onRefresh = refresh,
                        content = {
                            items(
                                count = accountTransactionsPaging.itemCount,
                                contentType = { LAZY_ITEM_TRANSACTION },
                                key = accountTransactionsPaging.itemKey { transaction -> transaction.transaction.transactionID },
                                itemContent = { index ->
                                    accountTransactionsPaging[index]?.let { transactionWithDetails ->
                                        TransactionCard(
                                            modifier = Modifier.animateItem(),
                                            account = transactionWithDetails.accountName,
                                            title = transactionWithDetails.transaction.title,
                                            amount = transactionWithDetails.transaction.formattedAmount,
                                            isPending = transactionWithDetails.transaction.pending,
                                            date = transactionWithDetails.transaction.transactionAtDate,
                                            transactionType = transactionWithDetails.transactionTypeID.asString(),
                                            isPay = transactionWithDetails.isPositive,
                                            currency = transactionWithDetails.currencySymbol,
                                            onClick = { navigateToCreateTransaction(account) }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }

        val accountDetailsFloatingToolbar = rememberAccountDetailsFloatingToolbar(
            accountUIState = accountUIState,
            popup = navigateBack,
            editAccount = {
                accountWithDetails?.account?.let { navigateToEditAccount(it) }
            },
            createTransaction = {
                accountWithDetails?.account?.let { navigateToCreateTransaction(it) }
            },
        )
        AMHorizontalFloatingToolbar(accountDetailsFloatingToolbar)
    }


}


@Composable
private fun rememberAccountDetailsFloatingToolbar(
    accountUIState: UIStates,
    popup: () -> Unit,
    editAccount: () -> Unit,
    createTransaction: () -> Unit,
): FloatingToolBarState =
    remember(accountUIState) {
        when (accountUIState) {
            is UIStates.Loading -> FloatingToolBarState.Loading
            is UIStates.Error -> FloatingToolBarState.Error(
                errorMessage = R.string.something_wrong.asAmText(),
                backButton = IconButton(
                    amIconsType = AmIcons.ArrowBack,
                    onClick = popup
                )
            )


            is UIStates.Success -> FloatingToolBarState.Content(
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
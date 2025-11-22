package com.awesome.manager.feature.transaction.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmTextWithLabel
import com.awesome.manager.core.designsystem.text.asString
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.ui.card.AccountCardWithDetails
import com.awesome.manager.core.ui.card.BalanceData

@Composable
fun TransactionDetailsRoute(
    navigateToAccountDetails: (accountID: String) -> Unit,
    navigateToTransactionEditor: (accountID: String, transactionID: String) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel(),
) {

    val accountUIState: UIStates =
        transactionDetailsViewModel.accountUIState.collectAsStateWithLifecycle().value

    val transactionUIState: UIStates =
        transactionDetailsViewModel.transactionUIState.collectAsStateWithLifecycle().value

    val account: AmAccountWithDetails? =
        transactionDetailsViewModel.account.collectAsStateWithLifecycle().value
    val transaction: AmTransactionWithDetails? =
        transactionDetailsViewModel.transaction.collectAsStateWithLifecycle().value

    val transactionDetailsActions: TransactionDetailsActions =
        transactionDetailsViewModel.transactionDetailsActions.collectAsStateWithLifecycle().value
    LaunchedEffect(transactionDetailsActions) {
        when (transactionDetailsActions) {
            is TransactionDetailsActions.Idle -> Unit
            is TransactionDetailsActions.AccountDetailsNavigation ->
                navigateToAccountDetails(transactionDetailsActions.account.accountID)

            is TransactionDetailsActions.TransactionEditorNavigation ->
                navigateToTransactionEditor(
                    transactionDetailsActions.transaction.accountID,
                    transactionDetailsActions.transaction.transactionID,
                )
        }
        if (transactionDetailsActions !is TransactionDetailsActions.Idle)
            transactionDetailsViewModel.doneTransactionsDetailsAction()
    }


    TransactionDetailsScreen(
        accountUIState = accountUIState,
        transactionUIState = transactionUIState,
        account = account,
        transaction = transaction,
    )
}

@Composable
fun TransactionDetailsScreen(
    accountUIState: UIStates,
    transactionUIState: UIStates,
    account: AmAccountWithDetails?,
    transaction: AmTransactionWithDetails?,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AmPadding.Details.value),
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = account,
            label = "ACCOUNT_DETAILS",
            contentAlignment = Alignment.TopCenter
        ) { accountWithDetails ->
            when (accountWithDetails) {
                null -> Unit
                else -> AccountCardWithDetails(
                    modifier = Modifier.fillMaxWidth(),
                    title = accountWithDetails.account.name,
                    imageUrl = accountWithDetails.account.imageUrl,
                    loading = accountWithDetails.account.pending,
                    balanceData = BalanceData.generate(
                        income = accountWithDetails.formattedIncome,
                        expenses = accountWithDetails.formattedExpenses,
                        netIncomeAbs = accountWithDetails.formattedNetIncome,
                        isPositiveIncome = accountWithDetails.isPositiveIncome,
                        debtor = accountWithDetails.formattedDebtor,
                        creditor = accountWithDetails.formattedCreditor,
                        netDebtorAbs = accountWithDetails.formattedNetDebtor,
                        isPositiveDebtor = accountWithDetails.isPositiveDebtor,
                        currencySymbol = accountWithDetails.currencySymbol,
                    )
                )
            }
        }

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = transaction,
            label = "TRANSACTION_DETAILS",
            contentAlignment = Alignment.TopCenter
        ) { transactionWithDetails ->
            when (transactionWithDetails) {
                null -> Unit
                else -> Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Title",
                        text = transactionWithDetails.transaction.title.ifBlank {
                            stringResource(
                                R.string.no_title
                            )
                        },
                        positive = transactionWithDetails.isPositive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Description",
                        text = transactionWithDetails.transaction.subtitle.ifBlank {
                            stringResource(
                                R.string.no_description
                            )
                        },
                        positive = transactionWithDetails.isPositive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.amount),
                        text = (transactionWithDetails.transaction.amount).toString(),
                        positive = transactionWithDetails.isPositive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.date),
                        text = transactionWithDetails.transaction.transactionAtDate,
                        positive = transactionWithDetails.isPositive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.payment_type),
                        text = transactionWithDetails.transactionTypeID.asString(),
                        positive = transactionWithDetails.isPositive
                    )
                }
            }
        }
    }


}
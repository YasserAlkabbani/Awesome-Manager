package com.awesome.manager.feature.transaction.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.component.AmTextWithLabel
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.AccountCard

@Composable
fun TransactionDetailsRoute(
    navigateBack: () -> Unit,
    navigateToAccount: (AmAccount) -> Unit,
    navigateToCreateTransaction: (AmAccount) -> Unit,
    navigateToEditTransaction: (AmTransaction) -> Unit,
    updateAppBarState: (appBarData: AppBarData?) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel()
) {

    val transactionDetailsState = transactionDetailsViewModel.transactionDetailsState

    val transaction = transactionDetailsState.transaction.collectAsState().value
    LaunchedEffect(key1 = transaction, block = {
        updateAppBarState(
            AppBarData(
                title = transaction?.title.orEmpty(),
                startIcon = AmIcons.ArrowBack to navigateBack,
                endIcon = AmIcons.Edit to {
                    transactionDetailsState.startEditTransactionNavigation(
                        transaction
                    )
                },
            )
        )
    })

    val accountNavigation = transactionDetailsState.accountNavigation.collectAsState().value
    LaunchedEffect(key1 = accountNavigation, block = {
        accountNavigation?.let { amAccount ->
            transactionDetailsState.doneAccountNavigation()
            navigateToAccount(amAccount)
        }
    })

    val createTransactionNavigation =
        transactionDetailsState.createTransactionNavigation.collectAsState().value
    LaunchedEffect(key1 = createTransactionNavigation, block = {
        createTransactionNavigation?.let { amAccount ->
            transactionDetailsState.doneCreateTransactionNavigation()
            navigateToCreateTransaction(amAccount)
        }
    })

    val transactionNavigation =
        transactionDetailsState.editTransactionNavigation.collectAsState().value
    LaunchedEffect(key1 = transactionNavigation, block = {
        transactionNavigation?.let { amTransaction ->
            transactionDetailsState.doneEditTransactionNavigation()
            navigateToEditTransaction(amTransaction)
        }
    })

    TransactionDetailsScreen(transactionDetailsState)
}

@Composable
fun TransactionDetailsScreen(
    transactionDetailsState: TransactionDetailsState
) {

    val account = transactionDetailsState.account.collectAsState().value
    val transaction = transactionDetailsState.transaction.collectAsState().value

    Column(
        modifier = Modifier.padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {

        AnimatedVisibility(account != null) {
            if (account != null) {
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    creditor = account.creditor,
                    debtor = account.debtor,
                    currency = account.currency.currencyCode,
                    loading = account.pending,
                    onClick = { transactionDetailsState.startAccountNavigation(account) },
                    onAddTransaction = {
                        transactionDetailsState.startCreateTransactionNavigation(
                            account
                        )
                    },
                    onEditTransaction = null
                )
            }
        }

        AmTextWithLabel(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.transaction_subject),
            text = transaction?.title,
            positive = transaction?.paymentTransaction,
        )
        AmTextWithLabel(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.transaction_description),
            text = transaction?.subtitle,
            positive = transaction?.paymentTransaction
        )
        AmTextWithLabel(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.amount),
            text = (transaction?.amount ?: 0.0).toString(),
            positive = transaction?.paymentTransaction
        )

        AmTextWithLabel(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.payment_type),
            text = transaction?.transactionType?.title,
            positive = transaction?.paymentTransaction
        )
    }
}
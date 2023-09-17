package com.awesome.manager.feature.account.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.CustomAppBar
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.TransactionCard

@Composable
fun AccountDetailsRoute(
    navigateBack:()->Unit,
    navigateToTransaction:(transactionId:String)->Unit,
    accountDetailsViewModel: AccountDetailsViewModel= hiltViewModel()
){
    val accountDetailsState: AccountDetailsState =accountDetailsViewModel.accountDetailsState


    val backNavigationState=accountDetailsState.backNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = backNavigationState, block = {
        backNavigationState?.let {
            accountDetailsState.doneBackNavigation()
            navigateBack()
        }
    })

    val transactionNavigation=accountDetailsState.transactionNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = transactionNavigation, block = {
        transactionNavigation?.let {
            navigateToTransaction(it)
            accountDetailsState.doneTransactionNavigation()
        }
    })

    AccountDetailsScreen(accountDetailsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailsScreen(accountDetailsState: AccountDetailsState){

    val account: AmAccount? =accountDetailsState.amAccount.collectAsStateWithLifecycle().value
    val transactions: List<AmTransaction> =accountDetailsState.amTransactions.collectAsStateWithLifecycle().value

    CustomAppBar(
        startIcon = AmIcons.ArrowBack to accountDetailsState::startBackNavigation,
        endIcon = null,
        title = account?.name.orEmpty()
    )


    Column {

        account?.let {account->
            AccountCard(
                modifier = Modifier,
                title = account.name,
                imageUrl = account.imageUrl,
                creditor = account.creditor,
                debtor = account.debtor,
                currency = account.currency.currencyName,
                loading = account.pending,
                onClick = {  },
                onAddTransaction = null,
                onEditTransaction = null
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp) ,
                content = {
                    items(
                        items = transactions,
                        contentType = {"TRANSACTIONS"},
                        key = {transactions->transactions.id},
                        itemContent = {transaction->
                            TransactionCard(
                                modifier = Modifier.animateItemPlacement(),
                                title = transaction.title,
                                subTitle = transaction.subtitle,
                                amount = transaction.amount,
                                pending = transaction.pending,
                                date = transaction.updatedAt,
                                transactionType = transaction.transactionType.title,
                                isPay = transaction.paymentTransaction,
                                currency = account.currency.currencySymbol,
                                createdBy = transaction.creatorUserId,
                                onClick = {}
                            )
                        }
                    )
                }
            )
        }

    }



}
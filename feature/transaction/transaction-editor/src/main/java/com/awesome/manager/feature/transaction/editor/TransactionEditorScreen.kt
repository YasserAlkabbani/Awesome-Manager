package com.awesome.manager.feature.transaction.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmBottomSheetState
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmModelBottomSheet
import com.awesome.manager.core.designsystem.component.AmSwitch
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconButton
import com.awesome.manager.core.designsystem.component.buttons.AmTextButton
import com.awesome.manager.core.designsystem.component.rememberAmBottomSheetState
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
    onBack:()->Unit
){

    val transactionEditorState:TransactionEditorState =transactionEditorViewModel.transactionEditorState

    val searchForAccountSheetState: AmBottomSheetState = rememberAmBottomSheetState()
    val searchForAccount=transactionEditorState.searchForAnAccount.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = searchForAccount, block = {
        if (searchForAccount){
            searchForAccountSheetState.open()
        }else{
            searchForAccountSheetState.close()
        }
    })
    LaunchedEffect(key1 = searchForAccountSheetState.isOpenButtonSheet.value, block = {
        if (!searchForAccountSheetState.isOpenButtonSheet.value){
            transactionEditorState.doneSearchForAnAccount()
        }
    })

    val accountSearchKey=transactionEditorState.accountSearchKey.collectAsStateWithLifecycle().value
    val accountSearchResult=transactionEditorState.accountSearchResult.collectAsStateWithLifecycle().value
    AmModelBottomSheet(
        amBottomSheetState = searchForAccountSheetState,
        content = {
            AmTextField(
                hint = "Search For Account", icon = AmIcons.Search, label = "Something..",
                text = accountSearchKey,
                onTextChange = transactionEditorState::updateAccountSearchKey
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.5.dp),
                content = {
                    items(
                        items = accountSearchResult,
                        contentType = { "ACCOUNTS" },
                        key = { account -> account.id },
                        itemContent = { account ->
                            AccountCard(
                                modifier = Modifier.animateItemPlacement(),
                                title = account.name,
                                imageUrl = account.imageUrl,
                                creditor = account.creditor,
                                debtor = account.debtor,
                                currency = account.currency.currencySymbol,
                                loading = account.pending,
                                onClick = {
                                    transactionEditorState.updateAccountId(account.id)
                                    transactionEditorState.doneSearchForAnAccount()
                                },
                                onAddTransaction = null,
                                onEditTransaction = null
                            )
                        }
                    )
                }
            )
        }
    )



    TransactionEditorScreen(transactionEditorState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEditorScreen(transactionEditorState: TransactionEditorState){

    val account=transactionEditorState.account.collectAsStateWithLifecycle().value
    val transactionTypes=transactionEditorState.transactionTypes.collectAsStateWithLifecycle().value
    val transactionType=transactionEditorState.transactionType.collectAsStateWithLifecycle().value
    val title=transactionEditorState.title.collectAsStateWithLifecycle().value
    val subtitle=transactionEditorState.subtitle.collectAsStateWithLifecycle().value
    val amount=transactionEditorState.amount.collectAsStateWithLifecycle().value
    val payTransaction=transactionEditorState.payTransaction.collectAsStateWithLifecycle().value

    Column(

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AmFilledTonalIconButton(amIconsType = AmIcons.ArrowBack, isPositive = false, onClick = transactionEditorState::startPoup)
            AmText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f),
                text = stringResource(R.string.create_transaction),
                style = MaterialTheme.typography.titleMedium
            )
            AmFilledTonalIconButton(amIconsType = AmIcons.Save, isPositive = true, onClick = transactionEditorState.createTransaction)
        }

        Column(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            AnimatedVisibility(account==null){
                    AmCard(
                        modifier = Modifier.fillMaxWidth(),
                        loading = false, positive = true,
                        onClick = transactionEditorState::startSearchForAnAccount,
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AmIcon(
                                    modifier = Modifier.size(30.dp),
                                    amIconsType = AmIcons.Search
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                AmText(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Search For An Account",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    )
            }

            AnimatedVisibility(account!=null){
                if (account!=null){
                    AccountCard(
                        modifier = Modifier,
                        title = account.name,
                        imageUrl = account.imageUrl,
                        creditor = account.creditor,
                        debtor = account.debtor,
                        currency = account.currency.currencySymbol,
                        loading = account.pending,
                        onClick = transactionEditorState::startSearchForAnAccount,
                        onAddTransaction = null,onEditTransaction = null
                    )
                }
            }

            AmTextField(hint = "Title", icon = AmIcons.Title, label = "Transaction Subject",
                text = title, onTextChange = transactionEditorState::updateTitle
            )
            AmTextField(hint = "Subtitle", icon = AmIcons.SubTitle, label = "Transaction Description",
                text = subtitle, onTextChange = transactionEditorState::updateSubTitle
            )
            AmTextField(hint = "Amount", icon = AmIcons.SubTitle, label = "5000.0",
                text = subtitle, onTextChange = transactionEditorState::updateAmount
            )
            AmSwitch(
                title="Payment Type", checkSubtitle = "Pay", unCheckSubtitle = "Receive",
                checked = payTransaction,
                onCheck = transactionEditorState::updateIsPayTransaction
            )

            AmChipsContainer(
                title = "Transaction Type",
                chipDataList = transactionTypes,
                onSelect = transactionEditorState::updateTransactionTypeId,
                selectedItem = transactionType
            )
        }

        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))

    }
}


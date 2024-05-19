package com.awesome.manager.feature.transaction.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.getChipData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val transactionEditorState: TransactionEditorState = transactionEditorViewModel.transactionEditorState

    val navigationAction = transactionEditorState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, transactionEditorState::resetNavigationAction)
    })

    val appBarAction = transactionEditorState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, transactionEditorState::resetAppBar)
    })

    val bottomSheetAction = transactionEditorState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction,transactionEditorState::idleBottomSheet)
    })

    val pickAction=transactionEditorState.pickAction.collectAsState().value
    LaunchedEffect(key1 = pickAction, block = {
        pickAction.sendMainAction(sendMainAction,transactionEditorState::resetPick)
    })

    val accountsSearchResult = transactionEditorState.accountsList.collectAsState().value
    val searchForAnAccountBottomSheet=transactionEditorState.searchForAnAccountBottomSheet.collectAsState().value
    LaunchedEffect(key1 = searchForAnAccountBottomSheet, block = {
        if (searchForAnAccountBottomSheet){
            transactionEditorState.doneSearchForAnAccountBottomSheet()
            transactionEditorState.showSearchForAccountBottomSheet(
                items = {
                    items(
                        items = accountsSearchResult,
                        contentType = { "ACCOUNTS" },
                        key = { account -> account.id },
                        itemContent = { account ->
                            AccountCard(
                                modifier = Modifier.animateItemPlacement(),
                                title = account.name,
                                imageUrl = account.imageUrl,
                                creditor = account.creditor,
                                debtor = account.debtor,
                                currency = account.currency.currencyCode,
                                loading = account.pending,
                                onClick = { transactionEditorState.selectAccount(account) },
                                onAddTransaction = null,
                                onEditTransaction = null
                            )
                        }
                    )
                },
                searchKey =transactionEditorState::updateSearchKey
            )
        }
    })

    val transactionData = transactionEditorState.transactionEditorInput.collectAsState().value
    if(transactionData is DataState.Success){
        when(transactionData.data.selectedAccount){
            null -> transactionEditorState.showCreateAppBar(
                title = "Select Account",
                onSave = transactionEditorState::requestSearchForAnAccountBottomSheet,
                onCancel = transactionEditorState::navigatePopBack
            )
            else -> when(transactionData.data){
                is TransactionEditorInput.TransactionEditorCreate -> {
                    transactionEditorState.showCreateAppBar(
                        title = stringResource(id = R.string.create_transaction),
                        onSave = transactionEditorState.createTransaction,
                        onCancel = transactionEditorState::navigatePopBack
                    )
                }
                is TransactionEditorInput.TransactionEditorUpdate -> {
                    transactionEditorState.showEditAppBar(
                        title = "Update Transaction",
                        onSave = transactionEditorState.createTransaction,
                        onCancel = transactionEditorState::navigatePopBack
                    )
                }
            }
        }
    }

    TransactionEditorScreen(transactionEditorState=transactionEditorState)
}

@Composable
fun TransactionEditorScreen(
    transactionEditorState: TransactionEditorState,
) {

    val transactionInput = transactionEditorState.transactionEditorInput.collectAsState().value
    val transactionData = transactionEditorState.transactionEditorData.collectAsState().value

    if (transactionInput is DataState.Success && transactionData is DataState.Success) {

        val transactionTypes = transactionData.data.transactionTypes
        val transaction = transactionInput.data

        val transactionTypeChip = remember(transactionTypes) {
            transactionTypes.map {
                getChipData(id=it.id, title = it.title, data = it)
            }
        }
        val paymentTypeChip= remember {
            listOf(
                getChipData(id = true, title =  "Pay", data = transactionEditorState::setAsPay),
                getChipData(id = false, title =  "Receive", data = transactionEditorState::setAsReceive)
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            transaction.selectedAccount?.let { account ->
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    creditor = account.creditor,
                    debtor = account.debtor,
                    currency = account.currency.currencyCode,
                    loading = account.pending,
                    onClick = transactionEditorState::requestSearchForAnAccountBottomSheet,
                    onAddTransaction = null, onEditTransaction = null
                )
            }

            AmTextField(
                hint = "Title", icon = AmIcons.Title, label = "Transaction Title",
                onTextChange = transactionEditorState::updateTitle,
                initTextValue = transaction.title,
                keyboardActions = KeyboardActions(), error = null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            AmTextField(
                hint = "Subtitle",label = "Transaction Subtitle",
                icon = AmIcons.SubTitle,
                onTextChange = transactionEditorState::updateSubTitle,
                initTextValue = transaction.subtitle,
                error = null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            AmTextField(
                hint = "Amount", icon = AmIcons.Money, label = "5000.0",
                initTextValue = transaction.amount.toString(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Number,
                ),
                reformatText = { value ->
                    value
                        .filter { it.isDigit() || it == '.' }
                        .let {
                            if (value.count { it == '.' } < 2) it else value.substringBeforeLast(
                                "."
                            )
                        }
                        .let {
                            val splitNumber = it.split('.')
                            val newBefore = splitNumber.first().ifBlank { "0" }.take(20)
                            val newAfter =
                                splitNumber.getOrNull(1)?.take(3)?.let { ".".plus(it) }.orEmpty()
                            "$newBefore$newAfter"
                        }
                },
                onTextChange = transactionEditorState::updateAmount,
                error = null,
            )

            AmFilledTonalIconWithTextButton(
                text = "Set data", amIconsType = AmIcons.Date, positive = null, loading = false,
                onClick = {
                    transactionEditorState.pickDate(
                        initTime = transaction.transactionAtTimestamp,
                        setDate = transactionEditorState::updateTransactionAt,
                        dismiss = transactionEditorState::resetPick
                    )
                }
            )

            AmChipsContainer(
                title = stringResource(R.string.transaction_type),
                chipDataList = transactionTypeChip,
                onSelect = {transactionEditorState.selectTransactionType(it.data)},
                selectedItem = transaction.selectedTransactionType?.id,
                content = null
            )
            AmChipsContainer(
                title = "Payment Type",
                chipDataList = paymentTypeChip,
                onSelect = {it.data()},
                selectedItem = transaction.paymentTransaction,
                content = null
            )
        }
    }




}


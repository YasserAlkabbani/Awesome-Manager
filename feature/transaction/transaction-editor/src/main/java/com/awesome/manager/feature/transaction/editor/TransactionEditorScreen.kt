package com.awesome.manager.feature.transaction.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmSwitch
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData

@Composable
fun TransactionEditorRoute(
    sendMainAction: (MainActions) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState

//    val searchForAccountSheetState: AmBottomSheetState = rememberAmBottomSheetState()
    val bottomSheetAction =
        transactionEditorState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendAction(sendMainAction)
    })

    val navigationAction=
        transactionEditorState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendAction(sendMainAction,transactionEditorState::resetNavigationAction)
    })

    val appBarAction=
        transactionEditorState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendAction(sendMainAction)
    })

//    val focusManager = LocalFocusManager.current
//    LaunchedEffect(key1 = searchForAccount, block = {
//        if (searchForAccount) {
//            focusManager.clearFocus()
//            searchForAccountSheetState.open()
//        } else {
//            searchForAccountSheetState.close()
//        }
//    })
//    LaunchedEffect(key1 = searchForAccountSheetState.isOpenButtonSheet.value, block = {
//        if (!searchForAccountSheetState.isOpenButtonSheet.value) {
//            transactionEditorState.doneSearchForAnAccount()
//        }
//    })


//    val createTransactionText = stringResource(id = R.string.create_transaction)
//    val editTransactionText = stringResource(R.string.edit_account)
//    val transaction = transactionEditorState.selectedAccount.collectAsState().value
//    LaunchedEffect(key1 = transaction, block = {
//        when (transaction) {
//            null -> transactionEditorState.showCreateAppBar(
//                title = createTransactionText,
//                onSave = transactionEditorState.createTransaction,
//                onCancel = transactionEditorState::navigatePopBack
//            )
//
//            else -> transactionEditorState.showEditAppBar(
//                title = editTransactionText,
//                onSave = transactionEditorState.createTransaction,
//                onCancel = transactionEditorState::navigatePopBack
//            )
//        }
//    })


    TransactionEditorScreen(transactionEditorState)
}

@Composable
fun TransactionEditorScreen(transactionEditorState: TransactionEditorState) {

    val transactionData = transactionEditorState.transactionEditorData.collectAsState().value
    val transactionTypes = transactionEditorState.transactionTypes.collectAsState().value
    val transactionTypeChip = remember(transactionTypes) {
        transactionTypes.map {
            ChipData(
                id = it.id,
                title = it.title
            )
        }
    }

    if (transactionData is DataState.Success){
        val transaction=transactionData.data
        Column(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            AnimatedVisibility(transaction.selectedAccount == null) {
                AmCard(
                    modifier = Modifier.fillMaxWidth(),
                    loading = false, positive = null,
                    onClick = {},
                    content = {},
                )
            }

            AnimatedVisibility(transaction.selectedAccount != null) {
                transaction.selectedAccount?.let {account->
                    AccountCard(
                        modifier = Modifier,
                        title = account.name,
                        imageUrl = account.imageUrl,
                        creditor = account.creditor,
                        debtor = account.debtor,
                        currency = account.currency.currencyCode,
                        loading = account.pending,
                        onClick = {},
                        onAddTransaction = null, onEditTransaction = null
                    )
                }
            }

            AmTextField(
                hint = "Title", icon = AmIcons.Title, label = "Transaction Subject",
                onTextChange = transactionEditorState::updateTitle,
                keyboardActions = KeyboardActions(), error = null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            AmTextField(
                hint = "Subtitle",
                icon = AmIcons.SubTitle,
                label = "Transaction Description",
                onTextChange = transactionEditorState::updateSubTitle,
                error = null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            AmTextField(
                hint = "Amount", icon = AmIcons.Money, label = "5000.0",
                onTextChange = {}, error = null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Number,
                ),
                reformatText = { value ->
                    value
                        .filter { it.isDigit() || it == '.' }
                        .let { if (value.count { it == '.' } < 2) it else value.substringBeforeLast(".") }
                        .let {
                            val splitNumber = it.split('.')
                            val newBefore = splitNumber.first().ifBlank { "0" }.take(20)
                            val newAfter = splitNumber.getOrNull(1)?.take(3)?.let { ".".plus(it) }.orEmpty()
                            "$newBefore$newAfter"
                        }
                }
            )

            AmChipsContainer(
                title = stringResource(R.string.transaction_type),
                chipDataList = transactionTypeChip,
                onSelect = {},
                selectedItem = transaction.selectedTransactionType?.id,
                content = {
                    AmSwitch(
                        modifier = Modifier.padding(6.dp),
                        title = stringResource(R.string.payment_type),
                        checkSubtitle = stringResource(R.string.receive),
                        unCheckSubtitle = stringResource(R.string.pay),
                        checked = transaction.isPaymentTransaction,
                        onCheck = {  }
                    )
                }
            )
        }
    }
}


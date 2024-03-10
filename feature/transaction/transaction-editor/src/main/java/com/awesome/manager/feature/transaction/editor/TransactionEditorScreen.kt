package com.awesome.manager.feature.transaction.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.ui_actions.AppBarAction
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmBottomSheetState
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmModelBottomSheet
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmSwitch
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.rememberAmBottomSheetState
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    sendMainAction: (MainActions) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState

    val searchForAccountSheetState: AmBottomSheetState = rememberAmBottomSheetState()
    val searchForAccount =
        transactionEditorState.searchForAnAccountBottomSheet.collectAsState().value

    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = searchForAccount, block = {
        if (searchForAccount) {
            focusManager.clearFocus()
            searchForAccountSheetState.open()
        } else {
            searchForAccountSheetState.close()
        }
    })
    LaunchedEffect(key1 = searchForAccountSheetState.isOpenButtonSheet.value, block = {
        if (!searchForAccountSheetState.isOpenButtonSheet.value) {
            transactionEditorState.doneSearchForAnAccount()
        }
    })

//    val accountSearchKey =
//        transactionEditorState.accountSearchKey.collectAsState().value
//    val accountSearchResult =
//        transactionEditorState.accountSearchResult.collectAsState().value

    AmModelBottomSheet(
        amBottomSheetState = searchForAccountSheetState,
        content = {

        }
    )

    val createTransactionText = stringResource(id = R.string.create_transaction)
    val editTransactionText = stringResource(R.string.edit_account)
    val transaction = transactionEditorState.selectedAccount.collectAsState().value
    LaunchedEffect(key1 = transaction, block = {
        when (transaction) {
            null -> transactionEditorState.showCreateAppBar(
                    title = createTransactionText,
                    onSave = transactionEditorState.createTransaction,
                    onCancel = transactionEditorState::navigatePopBack
                )

            else -> transactionEditorState.showEditAppBar(
                    title = editTransactionText,
                    onSave = transactionEditorState.createTransaction,
                    onCancel = transactionEditorState::navigatePopBack
                )
        }
    })


    TransactionEditorScreen(transactionEditorState)
}

@Composable
fun TransactionEditorScreen(transactionEditorState: TransactionEditorState) {

    val account = transactionEditorState.selectedAccount.collectAsState().value
    val transactionTypes =
        transactionEditorState.transactionTypes.collectAsState().value
    val transactionTypeChip = remember(transactionTypes) {
        transactionTypes.map {
            ChipData(
                id = it.id,
                title = it.title
            )
        }
    }
    val selectedTransactionType =
        transactionEditorState.selectedTransactionType.collectAsState().value
    val title = transactionEditorState.title.collectAsState().value
    val subtitle = transactionEditorState.subtitle.collectAsState().value
    val amount = transactionEditorState.amount.collectAsState().value
    val paymentTransaction =
        transactionEditorState.paymentTransaction.collectAsState().value

    Column(
        modifier = Modifier.padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        AnimatedVisibility(account == null) {
            AmCard(
                modifier = Modifier.fillMaxWidth(),
                loading = false, positive = null,
                onClick = transactionEditorState::startSearchForAnAccount,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AmSurface(
                            modifier = Modifier, positive = null, loading = false,
                            highPadding = false
                        ) {
                            AmIcon(
                                modifier = Modifier.size(26.dp),
                                amIconsType = AmIcons.Search
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        AmText(
                            modifier = Modifier.padding(8.dp),
                            text = "Search For An Account ..",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            )
        }

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
                    onClick = transactionEditorState::startSearchForAnAccount,
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
            onTextChange = transactionEditorState::updateAmount, error = null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done, keyboardType = KeyboardType.Number,
            ),
        )

        AmChipsContainer(
            title = stringResource(R.string.transaction_type),
            chipDataList = transactionTypeChip,
            onSelect = transactionEditorState::updateTransactionTypeId,
            selectedItem = selectedTransactionType?.id,
            content = {
                AmSwitch(
                    modifier = Modifier.padding(6.dp),
                    title = stringResource(R.string.payment_type),
                    checkSubtitle = stringResource(R.string.receive),
                    unCheckSubtitle = stringResource(R.string.pay),
                    checked = paymentTransaction,
                    onCheck = { transactionEditorState.updateTransactionPayment(it) }
                )
            }
        )
    }
}


package com.awesome.manager.feature.transaction.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmBottomSheetState
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmModelBottomSheet
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmSwitch
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.designsystem.component.rememberAmBottomSheetState
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
    updateAppBarState: (appBarData: AppBarData) -> Unit,
    onBack: () -> Unit
) {

    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState

    val popup = transactionEditorState.navigatePopup.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = popup, block = {
        if (popup) {
            transactionEditorState.donePop()
            onBack()
        }
    })

    val searchForAccountSheetState: AmBottomSheetState = rememberAmBottomSheetState()
    val searchForAccount =
        transactionEditorState.searchForAnAccountBottomSheet.collectAsStateWithLifecycle().value

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

    val accountSearchKey =
        transactionEditorState.accountSearchKey.collectAsStateWithLifecycle().value
    val accountSearchResult =
        transactionEditorState.accountSearchResult.collectAsStateWithLifecycle().value

    AmModelBottomSheet(
        amBottomSheetState = searchForAccountSheetState,
        content = {
            val focusRequester: FocusRequester = FocusRequester()
            AmTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        focusRequester.requestFocus()
                    },
                hint = "Search For Account", icon = AmIcons.Search, label = "Something..",
                text = accountSearchKey, error = null,
                onTextChange = transactionEditorState::updateAccountSearchKey
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
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
                                onClick = { transactionEditorState.selectAccount(account) },
                                onAddTransaction = null,
                                onEditTransaction = null
                            )
                        }
                    )
                }
            )
        }
    )

    val createTransactionText = stringResource(id = R.string.create_transaction)
    val editTransactionText = stringResource(R.string.edit_account)
    val transaction = transactionEditorState.selectedAccount.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = transaction, block = {
        delay(100)
        val title = when (transaction) {
            null -> createTransactionText
            else -> editTransactionText
        }
        updateAppBarState(
            AppBarData(
                title = title,
                startIcon = AmIcons.Close to transactionEditorState::startPop,
                endIcon = AmIcons.Save to transactionEditorState.createTransaction
            )
        )
    })


    TransactionEditorScreen(transactionEditorState)
}

@Composable
fun TransactionEditorScreen(transactionEditorState: TransactionEditorState) {

    val account = transactionEditorState.selectedAccount.collectAsStateWithLifecycle().value
    val transactionTypes =
        transactionEditorState.transactionTypes.collectAsStateWithLifecycle().value
    val transactionTypeChip = remember(transactionTypes) {
        transactionTypes.map {
            ChipData(
                id = it.id,
                title = it.title
            )
        }
    }
    val selectedTransactionType =
        transactionEditorState.selectedTransactionType.collectAsStateWithLifecycle().value
    val title = transactionEditorState.title.collectAsStateWithLifecycle().value
    val subtitle = transactionEditorState.subtitle.collectAsStateWithLifecycle().value
    val amount = transactionEditorState.amount.collectAsStateWithLifecycle().value
    val paymentTransaction =
        transactionEditorState.paymentTransaction.collectAsStateWithLifecycle().value

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
            text = title, onTextChange = transactionEditorState::updateTitle,
            keyboardActions = KeyboardActions(), error = null,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        AmTextField(
            hint = "Subtitle",
            icon = AmIcons.SubTitle,
            label = "Transaction Description",
            text = subtitle,
            onTextChange = transactionEditorState::updateSubTitle,
            error = null,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        AmTextField(
            hint = "Amount", icon = AmIcons.Money, label = "5000.0",
            text = amount, onTextChange = transactionEditorState::updateAmount, error = null,
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


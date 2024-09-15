package com.awesome.manager.feature.transaction.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.data.states.DataState
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToString
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.getChipData
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState

    val mainAction = transactionEditorState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, transactionEditorState::doneMainAction)
    }

    val accountsLazyPaging = transactionEditorState.accountsList.collectAsLazyPagingItems()
    val searchForAnAccountBottomSheet =
        transactionEditorState.searchForAnAccountBottomSheet.collectAsState().value
    val searchLabel = stringResource(id = R.string.search_in_accounts)

    LaunchedEffect(key1 = searchForAnAccountBottomSheet, block = {
        if (searchForAnAccountBottomSheet) {
            transactionEditorState.doneSearchForAnAccountBottomSheet()
            transactionEditorState.showSearchWithContentBottomSheet(
                searchLabel = searchLabel,
                content = {
                    AccountLazyColumn(
                        accountsLazyPaging = accountsLazyPaging,
                        onSelectAccount = transactionEditorState::selectAccount,
                        onDismiss = transactionEditorState::dismissBottomSheet
                    )
                },
                onReSearch = transactionEditorState::updateSearchKey,
                onSearchDone = transactionEditorState::dismissBottomSheet,
                initSearch = ""
            )
        }
    })

    val transactionData = transactionEditorState.transactionEditorInput.collectAsState().value
    LaunchedEffect(key1 = transactionData) {
        if (transactionData is DataState.Success) {
            val transactionEditor = transactionData.data
            val isValidateInput = transactionEditor.validateTransactionData != null
            val errorMessage =
                if (isValidateInput) null else context.getString(R.string.invalidate_input)
            val buttonText = when (transactionEditor.editorInputType) {
                EditorInputType.Create -> context.getString(R.string.create_transaction)
                EditorInputType.Edit -> context.getString(R.string.update_transaction)
            }
            when (transactionEditor.selectedAccount) {
                null -> Unit
                else -> Unit
//                null -> transactionEditorState.setForEditTransactionScreen(
//                    saveButton = AppBarButton(
//                        text = context.getString(R.string.select_account),
//                        click = transactionEditorState::requestSearchForAnAccountBottomSheet,
//                    ),
//                    cancelButton = true,
//                )
//
//                else -> {
//                    transactionEditorState.setForEditTransactionScreen(
//                        saveButton = AppBarButton(
//                            text = buttonText,
//                            click = transactionEditorState.createTransaction,
//                            errorMessage = errorMessage
//                        ),
//                        cancelButton = true,
//                    )
//                }
            }
        }
    }

    TransactionEditorScreen(transactionEditorState = transactionEditorState)
}

@Composable
fun TransactionEditorScreen(
    transactionEditorState: TransactionEditorState,
) {

    val transactionInput = transactionEditorState.transactionEditorInput.collectAsState().value
    val context = LocalContext.current

    if (transactionInput is DataState.Success) {

        val transaction = transactionInput.data

        val transactionTypeChipData = remember {
            transactionEditorState.transactionTypes.map {
                getChipData(id = it.name, title = context.enumToString(it), data = it)
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            transaction.selectedAccount?.let { account ->
                val balanceDetails = account.balanceDetails
                AccountCard(
                    modifier = Modifier,
                    title = account.name, imageUrl = account.imageUrl,
                    loading = account.pending, withDetails = true,
                    onClick = transactionEditorState::requestSearchForAnAccountBottomSheet,
                    onAddTransaction = null, onEditTransaction = null,
                    income = balanceDetails.formattedIncome,
                    expenses = balanceDetails.formattedExpenses,
                    netIncomeAbs = balanceDetails.formattedNetIncome,
                    debtor = balanceDetails.formattedDebtor,
                    creditor = balanceDetails.formattedCreditor,
                    netDebtorAbs = balanceDetails.formattedNetDebtor,
                    currencySymbol = balanceDetails.currency.currencySymbol,
                    isPositiveIncome = balanceDetails.isPositiveIncome,
                    isPositiveDebtor = balanceDetails.isPositiveDebtor,
                )
            }

            AmTextField(
                hint = "Title", icon = AmIcons.Title, label = "Transaction Title",
                onTextChange = transactionEditorState::updateTitle,
                initTextValue = transaction.title,
                keyboardActions = KeyboardActions(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            AmTextField(
                hint = "5000.0", icon = AmIcons.Money, label = "Amount",
                initTextValue = transaction.amount.toString(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Number,
                ),
                reformatText = { value ->
                    value
                        .filter { it.isDigit() || it == '.' }
                        .let {
                            if (value.count { it == '.' } < 2) it else value.substringBeforeLast(".")
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
            )
            AmTextField(
                hint = "Subtitle", label = "Transaction Subtitle",
                icon = AmIcons.SubTitle, singleLine = false,
                onTextChange = transactionEditorState::updateSubTitle,
                initTextValue = transaction.subtitle
            )

            AmFilledTonalIconWithTextButton(
                text = transaction.transactionAtDate,
                amIconsType = AmIcons.Date,
                positive = null,
                onClick = {
                    transactionEditorState.showPickDateBottomSheet(
                        initTime = transaction.transactionAtTimestamp,
                        setDate = transactionEditorState::updateTransactionAt,
                        dismiss = transactionEditorState::dismissBottomSheet
                    )
                }
            )

            AmChipsContainer(
                title = stringResource(R.string.transaction_type),
                chipDataList = transactionTypeChipData,
                onSelect = { transactionEditorState.selectTransactionType(it.data) },
                selectedItem = transaction.selectedTransactionType?.name,
                content = null
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountLazyColumn(
    accountsLazyPaging: LazyPagingItems<AmAccount>,
    onSelectAccount: (AmAccount) -> Unit, onDismiss: () -> Unit
) = AmLazyColumn(
    isRefreshing = true,
    onRefresh = {},
    content = {
        items(
            count = accountsLazyPaging.itemCount,
            contentType = { LAZY_ITEM_ACCOUNT },
            key = accountsLazyPaging.itemKey { transaction -> transaction.id },
            itemContent = { index ->
                accountsLazyPaging[index]?.let { account ->
                    val balanceDetails = account.balanceDetails
                    AccountCard(
                        modifier = Modifier.animateItemPlacement(),
                        title = account.name,
                        imageUrl = account.imageUrl,
                        loading = account.pending,
                        withDetails = false,
                        onClick = {
                            onSelectAccount(account)
                            onDismiss()
                        },
                        onAddTransaction = null,
                        onEditTransaction = null,
                        income = balanceDetails.formattedIncome,
                        expenses = balanceDetails.formattedExpenses,
                        netIncomeAbs = balanceDetails.formattedNetIncome,
                        debtor = balanceDetails.formattedDebtor,
                        creditor = balanceDetails.formattedCreditor,
                        netDebtorAbs = balanceDetails.formattedNetDebtor,
                        currencySymbol = balanceDetails.currency.currencySymbol,
                        isPositiveIncome = balanceDetails.isPositiveIncome,
                        isPositiveDebtor = balanceDetails.isPositiveDebtor
                    )
                }
            }
        )
    }
)


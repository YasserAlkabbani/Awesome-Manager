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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.enums.EditorInputType
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.appbar.sendMainAction
import com.awesome.manager.core.designsystem.actions.bottomsheet.sendMainAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.sendMainAction
import com.awesome.manager.core.designsystem.actions.picker.sendMainAction
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.getChipData
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val transactionEditorState: TransactionEditorStateMain =
        transactionEditorViewModel.transactionEditorState

    val navigationAction = transactionEditorState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(
            sendMainAction, transactionEditorState::doneNavigationAction
        )
    })

    val appBarAction = transactionEditorState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(
            sendMainAction, transactionEditorState::doneAppBarAction
        )
    })

    val bottomSheetAction = transactionEditorState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(
            sendMainAction, transactionEditorState::doneBottomSheetAction
        )
    })

    val pickAction = transactionEditorState.pickerAction.collectAsState().value
    LaunchedEffect(key1 = pickAction, block = {
        pickAction.sendMainAction(sendMainAction, transactionEditorState::donePickerAction)
    })

    val accountsLazyPaging = transactionEditorState.accountsList.collectAsLazyPagingItems()
    val searchForAnAccountBottomSheet =
        transactionEditorState.searchForAnAccountBottomSheet.collectAsState().value
    LaunchedEffect(key1 = searchForAnAccountBottomSheet, block = {
        if (searchForAnAccountBottomSheet) {
            transactionEditorState.doneSearchForAnAccountBottomSheet()
            transactionEditorState.showSearchForAccountBottomSheet(
                items = {
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
                                        transactionEditorState.selectAccount(account)
                                    },
                                    onAddTransaction = null,
                                    onEditTransaction = null,
                                    income = balanceDetails.income,
                                    expenses = balanceDetails.expenses,
                                    netIncomeAbs = balanceDetails.netIncomeAbs,
                                    debtor = balanceDetails.debtor,
                                    creditor = balanceDetails.creditor,
                                    netDebtorAbs = balanceDetails.netDebtorAbs,
                                    currencySymbol = balanceDetails.currency.currencySymbol,
                                    isPositiveIncome = balanceDetails.isPositiveIncome,
                                    isPositiveDebtor = balanceDetails.isPositiveDebtor
                                )
                            }
                        }
                    )
                },
                searchKey = transactionEditorState::updateSearchKey
            )
        }
    })

    val transactionData = transactionEditorState.transactionEditorInput.collectAsState().value
    val selectAccountText = stringResource(R.string.select_account)
    val createTransactionText = stringResource(R.string.create_transaction)
    val updateTransactionText = stringResource(R.string.update_transaction)
    val invalidateInputMessage = stringResource(R.string.invalidate_input)
    LaunchedEffect(key1 = transactionData) {
        if (transactionData is DataState.Success) {
            val transactionEditor = transactionData.data
            val isValidateInput = transactionEditor.validateTransactionData != null
            val errorMessage = if (isValidateInput) null else invalidateInputMessage
            val saveButton = if (isValidateInput) transactionEditorState.createTransaction else null
            when (transactionEditor.selectedAccount) {
                null -> transactionEditorState.setForEditTransactionScreen(
                    saveButtonText = selectAccountText,
                    onSaveButton = transactionEditorState::requestSearchForAnAccountBottomSheet,
                    onClickCancel = transactionEditorState::navigatePopBack,
                    errorMessage = null
                )

                else -> {
                    when (transactionEditor.editorInputType) {
                        EditorInputType.Create -> transactionEditorState.setForEditTransactionScreen(
                            saveButtonText = createTransactionText,
                            onSaveButton = saveButton,
                            onClickCancel = transactionEditorState::navigatePopBack,
                            errorMessage = errorMessage,
                        )

                        EditorInputType.Edit -> transactionEditorState.setForEditTransactionScreen(
                            saveButtonText = updateTransactionText,
                            onSaveButton = saveButton,
                            onClickCancel = transactionEditorState::navigatePopBack,
                            errorMessage = errorMessage
                        )
                    }

                }
            }
        }
    }

    TransactionEditorScreen(transactionEditorState = transactionEditorState)
}

@Composable
fun TransactionEditorScreen(
    transactionEditorState: TransactionEditorStateMain,
) {

    val transactionInput = transactionEditorState.transactionEditorInput.collectAsState().value

    if (transactionInput is DataState.Success) {

        val transactionTypes = transactionEditorState.transactionTypes
        val transaction = transactionInput.data

        val transactionTypeChip = remember(transactionTypes) {
            transactionTypes.map {
                getChipData(id = it.name, title = it.name, data = it)
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
                    onClick = null,
                    onAddTransaction = null, onEditTransaction = null,
                    income = balanceDetails.income, expenses = balanceDetails.expenses,
                    netIncomeAbs = balanceDetails.netIncomeAbs,
                    debtor = balanceDetails.debtor, creditor = balanceDetails.creditor,
                    netDebtorAbs = balanceDetails.netDebtorAbs,
                    currencySymbol = balanceDetails.currency.currencySymbol,
                    isPositiveIncome = balanceDetails.isPositiveIncome,
                    isPositiveDebtor = balanceDetails.isPositiveDebtor,
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
                hint = "Subtitle", label = "Transaction Subtitle",
                icon = AmIcons.SubTitle,
                onTextChange = transactionEditorState::updateSubTitle,
                initTextValue = transaction.subtitle,
                error = null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            AmTextField(
                hint = "5000.0", icon = AmIcons.Money, label = "Amount",
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
                text = transaction.transactionAtDate,
                amIconsType = AmIcons.Date,
                positive = null,
                loading = false,
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
                chipDataList = transactionTypeChip,
                onSelect = { transactionEditorState.selectTransactionType(it.data) },
                selectedItem = transaction.selectedTransactionType?.name,
                content = null
            )
        }
    }


}


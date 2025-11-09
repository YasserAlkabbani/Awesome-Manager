package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.EditorType
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AMHorizontalFloatingToolbar
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.FloatingToolBarState
import com.awesome.manager.core.designsystem.component.FloatingToolbarComponent
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.core.designsystem.text.asStringRes
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData
import timber.log.Timber

@Composable
internal fun AccountEditorRoute(
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel(),
    popup: () -> Unit
) {

    val accountNameTextFieldState = accountEditorViewModel.accountNameTextFieldState

    val editorType = accountEditorViewModel.editorType

    val accountEditorState =
        accountEditorViewModel.accountEditorState.collectAsStateWithLifecycle().value

    val transactionTypes =
        accountEditorViewModel.transactionTypes.collectAsStateWithLifecycle().value
    val currencies =
        accountEditorViewModel.currencies.collectAsStateWithLifecycle().value

    val accountImageUrl =
        accountEditorViewModel.imageURL.collectAsStateWithLifecycle().value
    val selectedCurrencyID =
        accountEditorViewModel.currencyID.collectAsStateWithLifecycle().value
    val selectedTransactionTypeID =
        accountEditorViewModel.transactionTypeID.collectAsStateWithLifecycle().value

    val accountEditorNavigation =
        accountEditorViewModel.accountEditorEvent.collectAsStateWithLifecycle().value
    LaunchedEffect(accountEditorNavigation) {
        accountEditorViewModel.doneAccountEditorEvent()
        when (accountEditorNavigation) {
            AccountEditorEvent.Popup -> popup()
            AccountEditorEvent.Idle -> Unit
        }
        if (accountEditorNavigation != AccountEditorEvent.Idle) accountEditorViewModel.doneAccountEditorEvent()
    }

    AccountEditorScreen(
        accountNameTextFieldState = accountNameTextFieldState,
        accountEditorState = accountEditorState,
        editorType = editorType,
        currencies = currencies,
        transactionTypes = transactionTypes,
        accountImageUrl = accountImageUrl,
        selectedCurrencyID = selectedCurrencyID,
        setCurrencyID = accountEditorViewModel::setCurrencyID,
        selectedTransactionTypeID = selectedTransactionTypeID,
        setTransactionTypeID = accountEditorViewModel::setTransactionTypeID,
        saveAccount = accountEditorViewModel::saveAccount,
        requestPopup = accountEditorViewModel::navigateBack
    )

}

@Composable
internal fun AccountEditorScreen(
    accountNameTextFieldState: TextFieldState,
    accountEditorState: AccountEditorState,
    editorType: EditorType,
    currencies: List<AmCurrency>?,
    transactionTypes: List<AmTransactionType>,
    accountImageUrl: String?,
    selectedCurrencyID: String?,
    setCurrencyID: (String) -> Unit,
    selectedTransactionTypeID: String?,
    setTransactionTypeID: (String) -> Unit,
    saveAccount: (AccountEditorState.ValidateInput) -> Unit,
    requestPopup: () -> Unit
) {

    val currencyChipData = remember(currencies) {
        currencies.orEmpty().map {
            Timber.d("TEST_CURRENCY $it")
            ChipData(
                id = it.id,
                title = it.name
            )
        }
    }
    val transactionTypeChipData = remember {
        transactionTypes.map {
            ChipData(
                id = it.id,
                titleRes = it.type.asStringRes(),
                title = it.type
            )
        }
    }

    val floatingToolBarState = rememberAccountEditorFloatingToolbar(
        accountEditorState = accountEditorState,
        editorType = editorType,
        popup = requestPopup,
        saveAccount = saveAccount
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AmPadding.COULMN_ITEMS_PADDING.value),
        ) {
            AmImage(
                modifier = Modifier
                    .padding(AmPadding.LARGE_IMAGE_PADDING.value)
                    .size(AmSize.LARGE_IMAGE_SIZE.value),
                imageUrl = accountImageUrl
            )
            AmTextField(
                modifier = Modifier
                    .padding(horizontal = AmPadding.HORIZONTAL_PADDING.value)
                    .fillMaxWidth(),
                textFieldState = accountNameTextFieldState,
                icon = AmIcons.Title,
                placeHolder = "Account Name",
                isValidateInput = true
            )
            AmChipsContainer(
                title = stringResource(R.string.currency),
                chipDataList = currencyChipData,
                selectedItemID = selectedCurrencyID,
                onSelect = { setCurrencyID(it.id) },
                content = null
            )
            AmChipsContainer(
                title = stringResource(R.string.default_transaction_type),
                chipDataList = transactionTypeChipData,
                selectedItemID = selectedTransactionTypeID,
                onSelect = { setTransactionTypeID(it.id) },
                content = null
            )
        }

        AMHorizontalFloatingToolbar(floatingToolBarState)
    }

}

@Composable
private fun rememberAccountEditorFloatingToolbar(
    accountEditorState: AccountEditorState,
    editorType: EditorType,
    popup: () -> Unit,
    saveAccount: (AccountEditorState.ValidateInput) -> Unit,
): FloatingToolBarState =
    remember(accountEditorState) {
        when (accountEditorState) {
            is AccountEditorState.Init -> FloatingToolBarState.Content(
                backButton = FloatingToolbarComponent.IconButton(
                    amIconsType = AmIcons.ArrowBack,
                    onClick = popup
                ),
                textMessage = R.string.whats_the_account_name.asAmText()
            )

            is AccountEditorState.InvalidateInput -> FloatingToolBarState.Error(
                errorMessage = R.string.invalid_account_name.asAmText(),
                backButton = FloatingToolbarComponent.IconButton(
                    amIconsType = AmIcons.ArrowBack,
                    onClick = popup
                )
            )

            is AccountEditorState.ValidateInput -> FloatingToolBarState.Content(
                textMessage = null,
                backButton = FloatingToolbarComponent.IconButton(
                    amIconsType = AmIcons.ArrowBack,
                    onClick = popup
                ),
                actionButton = FloatingToolbarComponent.ActionButton(
                    text = when (editorType) {
                        is EditorType.Create -> R.string.create_account
                        is EditorType.Update -> R.string.update_account
                    }.asAmText(),
                    amIconsType = when (editorType) {
                        is EditorType.Create -> AmIcons.ArrowForward
                        is EditorType.Update -> AmIcons.ArrowForward
                    },
                    onClick = { saveAccount(accountEditorState) },
                ),
            )
        }
    }
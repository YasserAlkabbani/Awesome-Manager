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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.dataOrNull
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AMHorizontalFloatingToolbar
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.FloatingToolBarState
import com.awesome.manager.core.designsystem.component.FloatingToolbarContent
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.component.text.isValidAccountName
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToString
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData

@Composable
internal fun AccountEditorRoute(
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel(),
    popup: () -> Unit
) {

    val accountNameTextFieldState = accountEditorViewModel.accountNameTextFieldState
    val transactionTypes = accountEditorViewModel.transactionTypes

    val currencies =
        accountEditorViewModel.currencies.collectAsStateWithLifecycle().value.dataOrNull()

    val accountImageUrl = accountEditorViewModel.accountImageURL.collectAsStateWithLifecycle().value
    val selectedCurrencyID = accountEditorViewModel.currencyID.collectAsStateWithLifecycle().value
    val selectedTransactionTypeID =
        accountEditorViewModel.transactionTypeID.collectAsStateWithLifecycle().value

    val setCurrencyID = accountEditorViewModel::setCurrencyID
    val setTransactionTypeID = accountEditorViewModel::setTransactionTypeID

    val popupStateValue = accountEditorViewModel.popup.collectAsStateWithLifecycle().value
    LaunchedEffect(popupStateValue) {
        if (popupStateValue) {
            accountEditorViewModel.donePopup()
            popup()
        }
    }

    AccountEditorScreen(
        accountNameTextFieldState = accountNameTextFieldState,
        currencies = currencies,
        transactionTypes = transactionTypes,
        accountImageUrl = accountImageUrl,
        selectedCurrencyID = selectedCurrencyID,
        setCurrencyID = setCurrencyID,
        selectedTransactionTypeID = selectedTransactionTypeID,
        setTransactionTypeID = setTransactionTypeID,
        saveAccount = accountEditorViewModel::saveAccount,
        requestPopup = accountEditorViewModel::requestPopup
    )

}

@Composable
internal fun AccountEditorScreen(
    accountNameTextFieldState: TextFieldState,
    currencies: List<AmCurrency>?,
    transactionTypes: List<AmTransactionType>,
    accountImageUrl: String?,
    selectedCurrencyID: String?,
    setCurrencyID: (String) -> Unit,
    selectedTransactionTypeID: String?,
    setTransactionTypeID: (String) -> Unit,
    saveAccount: () -> Unit,
    requestPopup: () -> Unit
) {
    val context = LocalContext.current
    val currencyChipData = remember(currencies) {
        currencies.orEmpty().map { ChipData(id = it.id, title = it.currencyName) }
    }
    val transactionTypeChipData = remember {
        transactionTypes.map {
            val title = context.enumToString(it)
            ChipData(id = it.id, title = title)
        }
    }
    val isValidInput: Boolean = remember(accountNameTextFieldState.text) {
        accountNameTextFieldState.text.toString().isValidAccountName()
    }

    Box(modifier = Modifier.fillMaxSize()) {
//        AMHorizontalFloatingToolbar(
//            FloatingToolBarState.InitState(
//                negativeButton = FloatingToolbarContent(
//                    text = "",
//                    amIconsType = AmIcons.ArrowBack,
//                    onClick = requestPopup
//                ),
//                initMessage = "Set The Account Name"
//            )
//        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //            .padding(top = AmPadding.TOP_PADDING.value)
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
                title = "Currency",
                chipDataList = currencyChipData,
                selectedItem = selectedCurrencyID,
                onSelect = { setCurrencyID(it.id) },
                content = null
            )
            AmChipsContainer(
                title = stringResource(R.string.default_transaction_type),
                chipDataList = transactionTypeChipData,
                selectedItem = selectedTransactionTypeID,
                onSelect = { setTransactionTypeID(it.id) },
                content = null
            )
        }


    }

}
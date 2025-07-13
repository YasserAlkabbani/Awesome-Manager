package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation.toRoute
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.common.asAmState
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.common.dataOrNull
import com.awesome.manager.core.common.filterSuccess
import com.awesome.manager.core.designsystem.component.asFlow
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
private const val IMAGE_URL: String = "IMAGE_URL"
private const val CURRENCY_ID: String = "CURRENCY_ID"
private const val TRANSACTION_TYPE: String = "TRANSACTION_TYPE"

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
) : ViewModel() {

    private fun String.setState(value: String): Unit = savedStateHandle.set(this, value)
    private fun String.getState(): StateFlow<String?> = savedStateHandle.getStateFlow(this, null)

    private val accountID: String? = savedStateHandle.toRoute<AccountEditorRoute>().accountID
    private suspend fun getAccount() = when (accountID) {
        null -> null
        else -> accountRepository.getAccountByID(accountID = accountID).first().account
    }

    private val _accountEditorState: MutableStateFlow<AccountEditorState> =
        MutableStateFlow(AccountEditorState.Init(accountID))
    val accountEditorState: StateFlow<AccountEditorState> = _accountEditorState


    val accountNameTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = ACCOUNT_NAME,
        saver = TextFieldState.Saver,
        init = { TextFieldState() },
    )

    val imageURL: StateFlow<String?> = IMAGE_URL.getState()
    fun setImageURL(imageURL: String) = IMAGE_URL.setState(imageURL)

    val currencyID: StateFlow<String?> = CURRENCY_ID.getState()
    fun setCurrencyID(currencyID: String) = CURRENCY_ID.setState(currencyID)

    val transactionTypeID: StateFlow<String?> = TRANSACTION_TYPE.getState()
    fun setTransactionTypeID(transactionType: String) = TRANSACTION_TYPE.setState(transactionType)

    private val _popup: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val popup: StateFlow<Boolean> = _popup
    fun requestPopup() = _popup.update { true }
    fun donePopup() = _popup.update { false }

    val transactionTypes: List<AmTransactionType> = AmTransactionType.getTypes()
    val currencies: StateFlow<AmState<List<AmCurrency>>> = currencyRepository
        .returnCurrencies()
        .asAmState(viewModelScope)

    init {
        syncAccountEditorState()
    }

    fun syncAccountEditorState() = viewModelScope.launch {

        when (val account = getAccount()) {
            null -> {
                accountNameTextFieldState.setTextAndPlaceCursorAtEnd("")
                setTransactionTypeID(transactionTypes.first().id)
                setCurrencyID(currencies.filterSuccess().first().first().id)
                setImageURL(images.random())
            }

            else -> {
                accountNameTextFieldState.setTextAndPlaceCursorAtEnd(account.name)
                setTransactionTypeID(account.defaultTransactionType.id)
                setCurrencyID(account.currency.id)
                setImageURL(account.imageUrl)
            }
        }

        combine(
            accountNameTextFieldState.asFlow(),
            imageURL,
            currencyID.flatMapLatest { id ->
                currencies.filterSuccess().map { it.firstOrNull { it.id == id } }
            },
            transactionTypeID.map { id -> transactionTypes.firstOrNull { it.id == id } }
        ) { accountName, imageURL, currency, transactionType ->

            val validAccountName: Boolean = !accountName.isBlank()
            val validImageUrl: Boolean = !imageURL.isNullOrBlank()
            val validCurrency: Boolean = currency != null
            val validTransactionType: Boolean = transactionType != null

            when {
                accountName.isBlank() -> AccountEditorState.Init(
                    editorState = accountEditorState.value.editorState
                )

                validAccountName && validCurrency && validTransactionType && validImageUrl ->
                    AccountEditorState.ValidateInput(
                        accountName = accountName,
                        imageURL = imageURL,
                        transactionType = transactionType,
                        currency = currency,
                        editorState = accountEditorState.value.editorState,
                    )

                else -> AccountEditorState.InvalidateInput(
                    inValidAccountName = !validAccountName,
                    invalidImageURL = !validImageUrl,
                    inValidTransactionType = !validTransactionType,
                    invalidCurrency = !validCurrency,
                    editorState = accountEditorState.value.editorState
                )
            }

        }
            .flowOn(Dispatchers.Default)
            .collect { newAccountEditeState -> _accountEditorState.update { newAccountEditeState } }
    }

    fun saveAccount() {
        viewModelScope.launch(Dispatchers.Default) {

            val currentUser = authRepository.currentUser().first()
            val account = getAccount()

            val accountID = account?.accountID ?: UUID.randomUUID().toString()
            val creatorUserID = account?.creatorUserID ?: currentUser.id

            val alreadyOnNetwork = account?.alreadyOnNetwork == true
            val updatePermission = account?.updatePermission != false

            val updatedAt = currentTime()
            val createdAt = account?.createdAt ?: updatedAt

            (accountEditorState.value as? AccountEditorState.ValidateInput)?.let { validateInput ->
                val amAccountWithBalance = AmAccount(
                    accountID = accountID,
                    creatorUserID = creatorUserID,
                    name = validateInput.accountName,
                    imageUrl = validateInput.imageURL,
                    defaultTransactionType = validateInput.transactionType,
                    pending = true,
                    alreadyOnNetwork = alreadyOnNetwork,
                    updatePermission = updatePermission,
                    currency = validateInput.currency,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
                accountRepository.upsertAccount(amAccountWithBalance)
                requestPopup()
            }

        }
    }

}

/// TEMP LIST
private val images: List<String> = listOf(
    "https://cdn.pixabay.com/photo/2023/06/11/13/14/boathouse-8055954_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/28/09/24/south-tyrol-8023224_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/26/08/18/dandelion-8018952_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/06/07/12/51/insect-8047159_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/04/11/12/58/berries-7917173_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/17/08/55/tree-7999477_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/06/01/03/41/landscape-8032549_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/19/18/07/bee-8005091_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/29/10/29/cobweb-8025639_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/04/03/18/37/nature-7897683_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/30/15/33/silver-gull-8028943_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/28/12/12/sanderling-8023532_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/04/22/20/29/needles-7944391_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/05/22/22/18/common-blue-butterfly-8011569_1280.jpg",
    "https://cdn.pixabay.com/photo/2023/04/18/15/52/flower-7935433_1280.jpg",
)
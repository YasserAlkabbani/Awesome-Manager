package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation.toRoute
import com.awesome.manager.core.common.asStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.designsystem.component.asFlow
import com.awesome.manager.core.designsystem.component.asString
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
private const val IMAGE_URL: String = "IMAGE_URL"
private const val CURRENCY_ID: String = "CURRENCY_ID"
private const val TRANSACTION_TYPE_ID: String = "TRANSACTION_TYPE_ID"

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val transactionTypeRepository: TransactionTypeRepository,
    private val currencyRepository: CurrencyRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private fun String.setState(value: String?): Unit = savedStateHandle.set(this, value)
    private fun String.getState(): StateFlow<String?> = savedStateHandle.getStateFlow(this, null)

    private val accountID: String? = savedStateHandle.toRoute<AccountEditorRoute>().accountID
    val createNewAccount: Boolean = accountID == null

    val accountNameTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = ACCOUNT_NAME,
        saver = TextFieldState.Saver,
        init = { TextFieldState("") },
    )

    private val _accountEditorState: MutableStateFlow<AccountEditorState> =
        MutableStateFlow(AccountEditorState.Init)
    internal val accountEditorState: StateFlow<AccountEditorState> = _accountEditorState

    private val _accountEditorNavigation: MutableStateFlow<AccountEditorNavigation?> =
        MutableStateFlow(null)
    internal val accountEditorNavigation: StateFlow<AccountEditorNavigation?> =
        _accountEditorNavigation

    fun doneAccountEditorNavigation() = _accountEditorNavigation.update { null }
    fun popup() = _accountEditorNavigation.update { AccountEditorNavigation.Popup }

    val imageURL: StateFlow<String?> = IMAGE_URL.getState()
    fun setImageURL(imageURL: String?) = IMAGE_URL.setState(imageURL)

    val currencyID: StateFlow<String?> = CURRENCY_ID.getState()
    fun setCurrencyID(currencyID: String) = CURRENCY_ID.setState(currencyID)

    val transactionTypeID: StateFlow<String?> = TRANSACTION_TYPE_ID.getState()
    fun setTransactionTypeID(transactionTypeID: String) =
        TRANSACTION_TYPE_ID.setState(transactionTypeID)

    val transactionTypes: StateFlow<List<AmTransactionType>> =
        transactionTypeRepository.returnTransactionsTypes().asStateFlow(viewModelScope, listOf())

    val currencies: StateFlow<List<AmCurrency>> =
        currencyRepository.returnCurrencies().asStateFlow(viewModelScope, listOf())

    init {
        syncAccountEditorState()
    }

    fun syncAccountEditorState() = viewModelScope.launch {


        when (accountID) {
            null -> {
                setTransactionTypeID(transactionTypes.first().first().id)
                setCurrencyID(currencies.first().first().id)
            }

            else -> {
                val account = accountRepository.getAccountByID(accountID).first().account
                accountNameTextFieldState.setTextAndPlaceCursorAtEnd(account.name)
                setTransactionTypeID(account.defaultTransactionTypeID)
                setCurrencyID(account.currencyID)
                account.imageUrl?.let { setImageURL(it) }
            }
        }

        combine(
            accountNameTextFieldState.asFlow(),
            imageURL,
            currencyID,
            transactionTypeID,
        ) { accountName, imageURL, currency, transactionType ->

            val validAccountName: Boolean = !accountName.isBlank()
            val validCurrency: Boolean = currency != null
            val validTransactionType: Boolean = transactionType != null

            val validateInput = validAccountName && validCurrency && validTransactionType

            when {
                accountName.isBlank() -> AccountEditorState.Init
                validateInput -> AccountEditorState.ValidateInput
                else -> AccountEditorState.InvalidateInput(
                    invalidAccountName = !validAccountName,
                    invalidTransactionType = !validTransactionType,
                    invalidCurrency = !validCurrency
                )
            }

        }
            .flowOn(Dispatchers.Default)
            .collect { newAccountEditeState -> _accountEditorState.update { newAccountEditeState } }
    }

    fun saveAccount() {
        viewModelScope.launch(Dispatchers.Default) {
            returnUpdatedAccount()?.let { account ->
                accountRepository.upsertAccount(account)
            }
        }
    }


    @OptIn(ExperimentalUuidApi::class)
    private suspend fun returnUpdatedAccount(): AmAccount? {
        data class AccountData(
            val accountID: String,
            val creatorUserID: String,
            val createdAt: Long,
        )

        val currentTime: Long = currentTime()
        val currentUser: AmUser = authRepository.currentUser().first()
        val updatedAt = currentTime
        val accountName: String = accountNameTextFieldState.asString()
        val imageURL: String? = imageURL.value
        val transactionTypeID: String? = transactionTypeID.value
        val currencyID: String? = currencyID.value
        val (accountID, creatorUserID, createdAt) = when (accountID) {
            null -> AccountData(
                accountID = Uuid.random().toString(),
                creatorUserID = currentUser.id,
                createdAt = currentTime,
            )

            else -> accountRepository.getAccountByID(accountID).first().account.let { account ->
                AccountData(
                    accountID = account.accountID,
                    creatorUserID = account.creatorUserID,
                    createdAt = account.createdAt,
                )
            }
        }


        val isValidInput =
            accountName.isNotBlank() && transactionTypeID != null &&
                    currencyID != null && currentUser.id == creatorUserID

        return when (isValidInput) {
            false -> null
            true -> AmAccount(
                accountID = accountID,
                creatorUserID = creatorUserID,
                name = accountName,
                imageUrl = imageURL,
                defaultTransactionTypeID = transactionTypeID,
                pending = true,
                currencyID = currencyID,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
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
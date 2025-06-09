package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private const val IMAGE_URL: String = "IMAGE_URL"
private const val CURRENCY_ID: String = "CURRENCY_ID"
private const val TRANSACTION_TYPE: String = "TRANSACTION_TYPE"

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val ssh: SavedStateHandle,
    currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val accountEditorArg: AccountEditorRoute = ssh.toRoute()
    private val accountID = accountEditorArg.accountID

    val accountNameTextFieldState: TextFieldState = TextFieldState()

    val transactionTypes: List<AmTransactionType> = AmTransactionType.getTypes()
    val currencies: StateFlow<AmState<List<AmCurrency>>> = currencyRepository.returnCurrencies()
        .asAmState(viewModelScope)

    fun setAccountImageURL(imageURL: String) = ssh.set(IMAGE_URL, imageURL)
    val accountImageURL: StateFlow<String> = ssh.getStateFlow(IMAGE_URL, "")

    fun setCurrencyID(currencyID: String) = ssh.set(CURRENCY_ID, currencyID)
    val currencyID: StateFlow<String> = ssh.getStateFlow(CURRENCY_ID, "")

    fun setTransactionTypeID(transactionType: String) = ssh.set(TRANSACTION_TYPE, transactionType)
    val transactionTypeID: StateFlow<String> = ssh.getStateFlow(TRANSACTION_TYPE, "")

    private val _popup: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val popup: StateFlow<Boolean> = _popup
    fun requestPopup()=_popup.update { true }
    fun donePopup()=_popup.update { false }

    init {
        setInitData()
    }

    fun setInitData() = viewModelScope.launch {

        val account = when (accountID) {
            null -> null
            else -> accountRepository.getAccountByID(accountID = accountID).first().account
        }

        val accountName: String = account?.name.orEmpty()
        val defaultTransactionType: AmTransactionType = account?.defaultTransactionType
            ?: transactionTypes.first()
        val currency: AmCurrency = account?.currency
            ?: currencies.filterSuccess().filter { it.isNotEmpty() }.first().first()
        val imageUrl: String = account?.imageUrl ?: images.random()

        accountNameTextFieldState.setTextAndPlaceCursorAtEnd(accountName)
        setTransactionTypeID(defaultTransactionType.id)
        setCurrencyID(currency.id)
        setAccountImageURL(imageUrl)

    }

    fun saveAccount() {
        viewModelScope.launch(Dispatchers.Default) {

            val currentUser = authRepository.currentUser().first()
            val account = when (accountID) {
                null -> null
                else -> accountRepository.getAccountByID(accountID = accountID).first().account
            }

            val accountID = account?.accountID ?: UUID.randomUUID().toString()
            val creatorUserID = account?.creatorUserID ?: currentUser.id

            val accountName = accountNameTextFieldState.text.toString()
            val imageURL: String? = accountImageURL.value
            val currency = currencies.value.dataOrNull()?.firstOrNull { currency ->
                currency.id == currencyID.value
            }
            val transactionType = transactionTypes.firstOrNull { transactionType ->
                transactionType.id == transactionTypeID.value
            }

            val alreadyOnNetwork = account?.alreadyOnNetwork == true
            val updatePermission = account?.updatePermission != false
            val updatedAt = currentTime()
            val createdAt = account?.createdAt ?: updatedAt

            when {
                imageURL == null -> Unit
                transactionType == null -> Unit
                currency == null -> Unit
                else -> {
                    val amAccountWithBalance = AmAccount(
                        accountID = accountID,
                        creatorUserID = creatorUserID,
                        name = accountName,
                        imageUrl = imageURL,
                        defaultTransactionType = transactionType,
                        pending = true,
                        alreadyOnNetwork = alreadyOnNetwork,
                        updatePermission = updatePermission,
                        currency = currency,
                        createdAt = createdAt,
                        updatedAt = updatedAt,
                    )
                    accountRepository.upsertAccount(amAccountWithBalance)
                    requestPopup()
                }
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
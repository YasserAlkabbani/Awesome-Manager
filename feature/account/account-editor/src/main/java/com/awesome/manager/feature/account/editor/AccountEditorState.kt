package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import java.util.UUID

private const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
private const val IMAGE_URL: String = "IMAGE_URL"
private const val CURRENCY: String = "CURRENCY_ID"
private const val TRANSACTION_TYPE: String = "TRANSACTION_TYPE"

class AccountEditorState(
    val currencies: StateFlow<AmUIState<List<AmCurrency>>>,
    val accountEditorData: StateFlow<AmUIState<AccountEditorData>>,
    val onUpsert: (UpsertAccount) -> Unit,
    private val savedStateHandle: SavedStateHandle,
) : ActionsManager() {

    val transactionTypes: List<AmTransactionType> = AmTransactionType.entries.toList()

    fun onUpdateAccountName(name: String) = savedStateHandle.set(ACCOUNT_NAME, name)
    val accountName: StateFlow<String> = savedStateHandle.getStateFlow(ACCOUNT_NAME, "")


    fun onUpdateAccountImage(imageUrl: String) = savedStateHandle.set(IMAGE_URL, imageUrl)
    val accountImageUrl: StateFlow<String> =
        savedStateHandle.getStateFlow(IMAGE_URL, images.random())


    fun onUpdateCurrency(currencyID: String) = savedStateHandle.set(CURRENCY, currencyID)
    val selectedCurrency: StateFlow<String?> = savedStateHandle.getStateFlow(CURRENCY, null)


    fun onUpdateTransactionType(transactionType: String) =
        savedStateHandle.set(TRANSACTION_TYPE, transactionType)

    val selectedTransactionType: StateFlow<String> =
        savedStateHandle.getStateFlow(TRANSACTION_TYPE, transactionTypes.first().name)

    suspend fun syncAccountData() {
        accountEditorData
            .filterIsInstance<AmUIState.Success<AccountEditorData>>()
            .filterIsInstance<AccountEditorData.EditAccount>()
            .mapLatest { it.account }
            .collect { account ->
                onUpdateAccountName(account.name)
                onUpdateAccountImage(account.imageUrl)
                onUpdateCurrency(account.balanceDetails.currency.id)
                onUpdateTransactionType(account.defaultTransactionType.name)
            }
    }

    suspend fun syncUIState() = accountEditorData
        .processUIState()
        .flatMapLatest { accountEditorData ->
            combine(
                accountName,
                accountImageUrl,
                selectedCurrency,
                selectedTransactionType
            ) { name, imageUrl, currencyID, transactionType ->

                when (name.isNotBlank() && currencyID != null) {
                    false -> dynamicFabMessage(
                        dynamicFabText = DynamicFabText.InvalidInput,
                        dynamicFabExtraButton = DynamicFabExtraButton.Back(::navigatePopBack)
                    )

                    true -> {
                        when (val accountData = accountEditorData.data) {
                            is AccountEditorData.CreateAccount -> {
                                val upsertAccount = accountData.asUpsert(
                                    name = name,
                                    imageUrl = imageUrl,
                                    currencyID = currencyID,
                                    defaultTransactionType = transactionType
                                )
                                dynamicFabButton(
                                    dynamicFabButton = DynamicFabButton.Create {
                                        onUpsert(
                                            upsertAccount
                                        )
                                    },
                                    dynamicFabExtraButton = DynamicFabExtraButton.Back(::navigatePopBack)
                                )
                            }

                            is AccountEditorData.EditAccount -> {
                                val upsertAccount = accountData.asUpsert(
                                    name = name,
                                    imageUrl = imageUrl,
                                    currencyID = currencyID,
                                    defaultTransactionType = transactionType
                                )
                                dynamicFabButton(
                                    dynamicFabButton = DynamicFabButton.Update {
                                        onUpsert(
                                            upsertAccount
                                        )
                                    },
                                    dynamicFabExtraButton = DynamicFabExtraButton.Back(::navigatePopBack)
                                )
                            }
                        }
                    }
                }
            }

        }.collect()

}

sealed interface AccountEditorData {

    fun asUpsert(
        name: String,
        imageUrl: String,
        currencyID: String,
        defaultTransactionType: String
    ): UpsertAccount

    data class CreateAccount(
        val accountId: String = UUID.randomUUID().toString(),
        val creatorUserID: String
    ) : AccountEditorData {
        override fun asUpsert(
            name: String,
            imageUrl: String,
            currencyID: String,
            defaultTransactionType: String
        ) = UpsertAccount(
            id = accountId,
            creatorUserId = creatorUserID,
            name = name,
            imageUrl = imageUrl,
            currencyId = currencyID,
            defaultTransactionType = defaultTransactionType,
        )
    }

    data class EditAccount(
        val account: AmAccount
    ) : AccountEditorData {
        override fun asUpsert(
            name: String,
            imageUrl: String,
            currencyID: String,
            defaultTransactionType: String
        ) = UpsertAccount(
            id = account.id,
            creatorUserId = account.creatorUserId,
            name = name,
            imageUrl = imageUrl,
            currencyId = currencyID,
            defaultTransactionType = defaultTransactionType,
        )
    }

    companion object {
        fun asCreate(currentUserId: String) = CreateAccount(creatorUserID = currentUserId)

        fun asEdit(account: AmAccount) = EditAccount(account = account)
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
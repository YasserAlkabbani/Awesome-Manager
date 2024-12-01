package com.awesome.manager.feature.account.editor

import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.ui.actions.filterSuccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import java.util.UUID

private const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
private const val IMAGE_URL: String = "IMAGE_URL"
private const val CURRENCY: String = "CURRENCY_ID"
private const val TRANSACTION_TYPE: String = "TRANSACTION_TYPE"

class AccountEditorState(
    val currencies: StateFlow<AmUIState<List<AmCurrency>>>,
    val accountEditorData: StateFlow<AmUIState<AccountEditorData>>,
    val onUpsert: (UpsertAccount) -> Unit,
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
) : ActionsManager() {

    val transactionTypes: List<AmTransactionType> = AmTransactionType.entries.toList()

    fun onUpdateAccountName(name: String) = ACCOUNT_NAME.setString(name)
    val accountName: StateFlow<String> = ACCOUNT_NAME.getString("")

    fun onUpdateAccountImage(imageUrl: String) = IMAGE_URL.setString(imageUrl)
    val accountImageUrl: StateFlow<String> = IMAGE_URL.getString(images.random())

    fun onUpdateCurrency(currencyID: String) = CURRENCY.setString(currencyID)
    val selectedCurrency: StateFlow<String> = CURRENCY.getString("")

    fun onUpdateTransactionType(transactionType: String) =
        TRANSACTION_TYPE.setString(transactionType)

    val selectedTransactionType: StateFlow<String> =
        TRANSACTION_TYPE.getString(transactionTypes.first().name)

    val accountEditorUI = accountEditorData
        .filterSuccessData()
        .setInitData()
        .flatMapLatest { accountEditorData ->
            combine(
                accountName,
                accountImageUrl,
                selectedCurrency,
                selectedTransactionType
            ) { name, imageUrl, currencyID, transactionType ->
                UpsertAccount(
                    id = accountEditorData.accountID,
                    creatorUserId = accountEditorData.creatorUserID,
                    name = name,
                    imageUrl = imageUrl,
                    currencyId = currencyID,
                    defaultTransactionType = transactionType
                ).processUIState(accountEditorData)
            }
        }


    private fun Flow<AccountEditorData>.setInitData()=onEach { accountEditorData ->
        if (accountEditorData is AccountEditorData.EditAccount){
            accountEditorData.account.apply {
                onUpdateAccountName(name)
                onUpdateAccountImage(imageUrl)
                onUpdateCurrency(balanceDetails.currency.id)
                onUpdateTransactionType(defaultTransactionType.name)
            }
        }
    }

    private fun UpsertAccount.processUIState(accountEditorData: AccountEditorData) {
        when (isValid()) {
            false -> dynamicFabInvalidInput(::navigatePopBack)
            true -> when (accountEditorData) {
                is AccountEditorData.CreateAccount -> dynamicFabCreateAccount(
                    onCreate = { onUpsert(this) },
                    navigatePopBack = ::navigatePopBack
                )

                is AccountEditorData.EditAccount -> dynamicFabUpdateAccount(
                    onUpdate = { onUpsert(this) },
                    navigatePopBack = ::navigatePopBack
                )
            }
        }
    }


}

sealed interface AccountEditorData {

    val accountID: String
    val creatorUserID: String

    data class CreateAccount(
        override val accountID: String = UUID.randomUUID().toString(),
        override val creatorUserID: String,
    ) : AccountEditorData

    data class EditAccount(
        override val accountID: String,
        override val creatorUserID: String,
        val account: AmAccount,
    ) : AccountEditorData

    companion object {
        fun create(currentUserId: String) = CreateAccount(
            creatorUserID = currentUserId
        )

        fun create(account: AmAccount) = EditAccount(
            accountID = account.id,
            creatorUserID = account.creatorUserID,
            account = account
        )
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
package com.awesome.manager.feature.account.editor

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.common.filterSuccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import java.util.UUID

private const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
private const val ACCOUNT_IMAGE_URL: String = "ACCOUNT_IMAGE_URL"
private const val ACCOUNT_CURRENCY: String = "ACCOUNT_CURRENCY"
private const val ACCOUNT_TRANSACTION_TYPE: String = "ACCOUNT_TRANSACTION_TYPE"

class AccountEditorState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val transactionTypes: List<AmTransactionType>,
    val currencies: StateFlow<AmState<List<AmCurrency>>>,
    val accountEditorData: StateFlow<AmState<AccountEditorData>>,
    private val upsertAccount: UpsertAccount.() -> Unit,
) {


    fun updateAccountName(name: String) = ACCOUNT_NAME.setString(name)
    val accountName: StateFlow<String> = ACCOUNT_NAME.getString("")

    fun updateAccountImage(imageUrl: String) = ACCOUNT_IMAGE_URL.setString(imageUrl)
    val accountImageUrl: StateFlow<String> = ACCOUNT_IMAGE_URL.getString(images.random())

    fun updateCurrency(currencyID: String) = ACCOUNT_CURRENCY.setString(currencyID)
    val selectedCurrency: StateFlow<String> = ACCOUNT_CURRENCY.getString("")

    fun updateTransactionType(transactionType: String) =
        ACCOUNT_TRANSACTION_TYPE.setString(transactionType)

    val selectedTransactionTypeID: StateFlow<String> =
        ACCOUNT_TRANSACTION_TYPE.getString(transactionTypes.first().id)

    val accountEditorUI = accountEditorData
        .filterSuccessData()
        .setInitData()
//        .flatMapLatest { accountEditorData ->
//            combine(
//                accountName,
//                accountImageUrl,
//                selectedCurrency,
//                selectedTransactionTypeID
//            ) { name, imageUrl, currencyID, transactionType ->
//                UpsertAccount(
//                    id = accountEditorData.accountID,
//                    creatorUserId = accountEditorData.creatorUserID,
//                    name = name,
//                    imageUrl = imageUrl,
//                    currencyId = currencyID,
//                    defaultTransactionTypeID = transactionType,
//                    alreadyOnNetwork = accountEditorData.alreadyOnNetwork()
//                ).processUIState(accountEditorData)
//            }
//        }


    private fun Flow<AccountEditorData>.setInitData() = onEach { accountEditorData ->
        when (accountEditorData) {
            is AccountEditorData.EditAccount -> accountEditorData.account.apply {
                updateAccountName(name)
                updateAccountImage(imageUrl)
                updateCurrency(balanceDetails.currency.id)
                updateTransactionType(defaultTransactionType.id)
            }

            else -> Unit
        }
    }

//    private fun UpsertAccount.processUIState(accountEditorData: AccountEditorData) {
//        when (isValid()) {
//            false -> dynamicFabInvalidInput(::navigatePopBack)
//            true -> when (accountEditorData) {
//                is AccountEditorData.CreateAccount -> dynamicFabCreateAccount(
//                    createAccount = { upsertAccount() },
//                    navigatePopBack = ::navigatePopBack
//                )
//
//                is AccountEditorData.EditAccount -> dynamicFabUpdateAccount(
//                    updateAccount = { upsertAccount() },
//                    navigatePopBack = ::navigatePopBack
//                )
//            }
//        }
//    }


}

sealed interface AccountEditorData {

    val accountID: String
    val creatorUserID: String
    fun alreadyOnNetwork() = this is EditAccount && account.alreadyOnNetwork

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
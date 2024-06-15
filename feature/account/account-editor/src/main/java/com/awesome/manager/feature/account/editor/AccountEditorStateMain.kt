package com.awesome.manager.feature.account.editor

import com.awesome.manager.core.common.enums.EditorInputType
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.setData
import com.awesome.manager.core.common.states.updateData
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class AccountEditorStateMain(
    val currencies: StateFlow<List<AmCurrency>>,
    val onSave: () -> Unit,
) : MainState() {

    val transactionTypes: List<AmTransactionType> = AmTransactionType.entries.toList()

    private val _accountEditorData: MutableStateFlow<DataState<AccountEditorData>> =
        MutableStateFlow(DataState.Loading)
    val accountEditorData: StateFlow<DataState<AccountEditorData>> =
        _accountEditorData.asStateFlow()

    fun asCreateAccount(creatorUserId: String) = _accountEditorData
        .setData {
            AccountEditorData(
                id = UUID.randomUUID().toString(), creatorUserId = creatorUserId,
                name = "", imageUrl = images.random(),
                currency = null, defaultTransactionType = null,
                editorInputType = EditorInputType.Create,
                allowToUpdateCurrency = true
            )
        }

    fun asEditAccount(currentUserId: String, amAccount: AmAccount,allowToUpdateCurrency: Boolean) {
        if (currentUserId == amAccount.creatorUserId) _accountEditorData.setData {
            AccountEditorData(
                id = amAccount.id, creatorUserId = amAccount.creatorUserId,
                name = amAccount.name, imageUrl = amAccount.imageUrl,
                currency = amAccount.balanceDetails.currency,
                defaultTransactionType = amAccount.defaultTransactionType,
                editorInputType = EditorInputType.Edit,
                allowToUpdateCurrency=allowToUpdateCurrency
            )
        }
        else navigatePopBack()
    }

    fun updateName(name: String) = _accountEditorData.updateData { it.updateName(name) }
    fun updateImageUrl(imageUrl: String) =
        _accountEditorData.updateData { it.updateImageUrl(imageUrl) }

    fun updateCurrency(amCurrency: AmCurrency) =
        _accountEditorData.updateData { it.updateSelectedCurrency(amCurrency) }

    fun updateDefaultTransactionType(transactionType: AmTransactionType) =
        _accountEditorData.updateData { it.updateDefaultTransactionType(transactionType) }

    fun checkValidateAccount(): UpsertAccount? =
        (accountEditorData.value as? DataState.Success)?.data?.validateAccountData

}

data class AccountEditorData(
    val id: String,
    val creatorUserId: String,
    val name: String,
    val imageUrl: String,
    val currency: AmCurrency?,
    val defaultTransactionType: AmTransactionType?,
    val editorInputType: EditorInputType,
    val allowToUpdateCurrency:Boolean
) {

    fun updateName(name: String): AccountEditorData = copy(name = name)

    fun updateImageUrl(imageUrl: String): AccountEditorData = copy(imageUrl = imageUrl)

    fun updateSelectedCurrency(currency: AmCurrency): AccountEditorData = copy(currency = currency)

    fun updateDefaultTransactionType(transactionType: AmTransactionType): AccountEditorData =
        copy(defaultTransactionType = transactionType)

    val validateAccountData: UpsertAccount? =
        if (name.isNotEmpty() && currency != null && defaultTransactionType != null)
            UpsertAccount(
                id = id, creatorUserId = creatorUserId, name = name,
                imageUrl = imageUrl, currencyId = currency.id,
                defaultTransactionType = defaultTransactionType,
            ) else null

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
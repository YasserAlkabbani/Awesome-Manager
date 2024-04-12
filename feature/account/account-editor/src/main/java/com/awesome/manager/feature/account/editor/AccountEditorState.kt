package com.awesome.manager.feature.account.editor

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.setData
import com.awesome.manager.core.common.states.updateData
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AccountEditorState(
    val currencies: StateFlow<List<AmCurrency>>,
    val transactionTypes: StateFlow<List<AmTransactionType>>,
    val onSave: () -> Unit,
) : MainActionsState() {

    private val _Edit_accountData: MutableStateFlow<DataState<EditAccountData>> =
        MutableStateFlow(DataState.Loading)
    val editAccountData: StateFlow<DataState<EditAccountData>> = _Edit_accountData
    fun asCreateAccount(creatorUserId: String) = _Edit_accountData
        .setData { EditAccountData.CreateEditAccountData(creatorUserId = creatorUserId) }

    fun asEditAccount(currentUserId: String, amAccount: AmAccount) {
        if (currentUserId == amAccount.id) _Edit_accountData.setData {
            EditAccountData.EditEditAccountData(
                id = amAccount.id, creatorUserId = amAccount.creatorUserId,
                name = amAccount.name, imageUrl = amAccount.imageUrl,
                currency = amAccount.currency,
                defaultTransactionType = amAccount.defaultTransactionType,
            )
        }
        else navigatePopBack()
    }

    fun updateName(name: String) = _Edit_accountData.updateData { it.updateName(name) }
    fun updateImageUrl(imageUrl: String) = _Edit_accountData.updateData { it.updateImageUrl(imageUrl) }
    fun updateCurrency(amCurrency: AmCurrency) =
        _Edit_accountData.updateData { it.updateSelectedCurrency(amCurrency) }

    fun updateDefaultTransactionType(transactionType: AmTransactionType) =
        _Edit_accountData.updateData { it.updateDefaultTransactionType(transactionType) }

    fun checkValidateAccount(): UpsertAccount? =
        (editAccountData.value as? DataState.Success)?.data?.validateAccountData()

}

sealed class EditAccountData {

    abstract fun updateName(name: String): EditAccountData
    abstract fun updateImageUrl(imageUrl: String): EditAccountData
    abstract fun updateSelectedCurrency(currency: AmCurrency): EditAccountData
    abstract fun updateDefaultTransactionType(transactionType: AmTransactionType): EditAccountData
    abstract fun validateAccountData(): UpsertAccount?

    abstract val id: String
    abstract val creatorUserId: String
    abstract val name: String
    abstract val imageUrl: String
    abstract val currency: AmCurrency?
    abstract val defaultTransactionType: AmTransactionType?

    data class CreateEditAccountData(
        override val id: String = UUID.randomUUID().toString(),
        override val creatorUserId: String,
        override val name: String="",
        override val imageUrl: String= images.random(),
        override val currency: AmCurrency?=null,
        override val defaultTransactionType: AmTransactionType?=null,
    ) : EditAccountData() {

        override fun updateName(name: String): CreateEditAccountData =
            copy(name = name)

        override fun updateImageUrl(imageUrl: String): CreateEditAccountData =
            copy(imageUrl = imageUrl)

        override fun updateSelectedCurrency(currency: AmCurrency): CreateEditAccountData =
            copy(currency = currency)

        override fun updateDefaultTransactionType(transactionType: AmTransactionType): CreateEditAccountData =
            copy(defaultTransactionType = transactionType)

        override fun validateAccountData(): UpsertAccount? =
            if (name.isNotEmpty() && currency != null && defaultTransactionType != null)
                UpsertAccount(
                    id = id, creatorUserId = creatorUserId, name = name,
                    imageUrl = imageUrl, currencyId = currency.id,
                    defaultTransactionTypeId = defaultTransactionType.id,
                ) else null

    }

    data class EditEditAccountData(
        override val id: String,
        override val creatorUserId: String,
        override val name: String,
        override val imageUrl: String,
        override val currency: AmCurrency,
        override val defaultTransactionType: AmTransactionType
    ) : EditAccountData() {

        override fun updateName(name: String): EditEditAccountData =
            copy(name = name)

        override fun updateImageUrl(imageUrl: String): EditEditAccountData =
            copy(imageUrl = imageUrl)

        override fun updateSelectedCurrency(currency: AmCurrency): EditEditAccountData =
            copy(currency = currency)

        override fun updateDefaultTransactionType(transactionType: AmTransactionType): EditEditAccountData =
            copy(defaultTransactionType = transactionType)

        override fun validateAccountData(): UpsertAccount? =
            if (name.isNotEmpty())
                UpsertAccount(
                    id = id, creatorUserId = creatorUserId, name = name,
                    imageUrl = imageUrl, currencyId = currency.id,
                    defaultTransactionTypeId = defaultTransactionType.id,
                ) else null

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
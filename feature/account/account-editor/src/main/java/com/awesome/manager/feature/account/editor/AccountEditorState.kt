package com.awesome.manager.feature.account.editor

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.setData
import com.awesome.manager.core.common.states.updateData
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AccountEditorState(
    val currencies: StateFlow<List<AmCurrency>>,
    val onSave: () -> Unit,
) : MainState() {

    val transactionTypes: List<AmTransactionType> = AmTransactionType.entries.toList()

    private val _accountEditorData: MutableStateFlow<DataState<AccountEditorData>> =
        MutableStateFlow(DataState.Loading)
    val accountEditorData: StateFlow<DataState<AccountEditorData>> = _accountEditorData
    fun asCreateAccount(creatorUserId: String) = _accountEditorData
        .setData { AccountEditorData.AccountEditorCreate(creatorUserId = creatorUserId) }

    fun asEditAccount(currentUserId: String, amAccount: AmAccount) {
        if (currentUserId == amAccount.creatorUserId) _accountEditorData.setData {
            AccountEditorData.AccountEditorUpdate(
                id = amAccount.id, creatorUserId = amAccount.creatorUserId,
                name = amAccount.name, imageUrl = amAccount.imageUrl,
                currency = amAccount.balanceDetails.currency,
                defaultTransactionType = amAccount.defaultTransactionType,
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
        (accountEditorData.value as? DataState.Success)?.data?.validateAccountData()

}

sealed class AccountEditorData {

    abstract fun updateName(name: String): AccountEditorData
    abstract fun updateImageUrl(imageUrl: String): AccountEditorData
    abstract fun updateSelectedCurrency(currency: AmCurrency): AccountEditorData
    abstract fun updateDefaultTransactionType(transactionType: AmTransactionType): AccountEditorData
    abstract fun validateAccountData(): UpsertAccount?

    abstract val id: String
    abstract val creatorUserId: String
    abstract val name: String
    abstract val imageUrl: String
    abstract val currency: AmCurrency?
    abstract val defaultTransactionType: AmTransactionType?

    data class AccountEditorCreate(
        override val id: String = UUID.randomUUID().toString(),
        override val creatorUserId: String,
        override val name: String = "",
        override val imageUrl: String = images.random(),
        override val currency: AmCurrency? = null,
        override val defaultTransactionType: AmTransactionType? = null,
    ) : AccountEditorData() {

        override fun updateName(name: String): AccountEditorCreate =
            copy(name = name)

        override fun updateImageUrl(imageUrl: String): AccountEditorCreate =
            copy(imageUrl = imageUrl)

        override fun updateSelectedCurrency(currency: AmCurrency): AccountEditorCreate =
            copy(currency = currency)

        override fun updateDefaultTransactionType(transactionType: AmTransactionType): AccountEditorCreate =
            copy(defaultTransactionType = transactionType)

        override fun validateAccountData(): UpsertAccount? =
            if (name.isNotEmpty() && currency != null && defaultTransactionType != null)
                UpsertAccount(
                    id = id, creatorUserId = creatorUserId, name = name,
                    imageUrl = imageUrl, currencyId = currency.id,
                    defaultTransactionType = defaultTransactionType,
                ) else null

    }

    data class AccountEditorUpdate(
        override val id: String,
        override val creatorUserId: String,
        override val name: String,
        override val imageUrl: String,
        override val currency: AmCurrency,
        override val defaultTransactionType: AmTransactionType
    ) : AccountEditorData() {

        override fun updateName(name: String): AccountEditorUpdate =
            copy(name = name)

        override fun updateImageUrl(imageUrl: String): AccountEditorUpdate =
            copy(imageUrl = imageUrl)

        override fun updateSelectedCurrency(currency: AmCurrency): AccountEditorUpdate =
            copy(currency = currency)

        override fun updateDefaultTransactionType(transactionType: AmTransactionType): AccountEditorUpdate =
            copy(defaultTransactionType = transactionType)

        override fun validateAccountData(): UpsertAccount? =
            if (name.isNotEmpty())
                UpsertAccount(
                    id = id, creatorUserId = creatorUserId, name = name,
                    imageUrl = imageUrl, currencyId = currency.id,
                    defaultTransactionType = defaultTransactionType,
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
package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.AmUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private const val NAME:String="NAME"
private const val IMAGE_URL:String="IMAGE_URL"
private const val CURRENCY_ID:String="CURRENCY_ID"
private const val DEFAULT_TRANSACTION_TYPE_ID:String="DEFAULT_TRANSACTION_TYPE_ID"

class AccountEditorState(
    private val savedStateHandle: SavedStateHandle,
    val currencies:StateFlow<List<AmCurrency>>,
    val transactionTypes: StateFlow<List<AmTransactionType>>,
    val onSave:()->Unit,
    coroutineScope: CoroutineScope
) {

    val name: StateFlow<String> = savedStateHandle.getStateFlow(NAME,"")
    fun updateName(newValue:String)=savedStateHandle.set(NAME,newValue)

    val imageUrl: StateFlow<String> = savedStateHandle.getStateFlow(IMAGE_URL,generateImageUrl())
    fun updateImageUrl(newValue:String)=savedStateHandle.set(IMAGE_URL,newValue)

    val selectedCurrency: StateFlow<AmCurrency?> = savedStateHandle.getStateFlow<String?>(CURRENCY_ID,null)
        .combine(currencies) {currencyId,currencies->
        currencies.firstOrNull { it.id==currencyId }
    }.flowOn(Dispatchers.Default).stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000),null)
    fun updateCurrency(currencyId:String)=savedStateHandle.set(CURRENCY_ID,currencyId)

    val defaultTransactionType: StateFlow<AmTransactionType?> = savedStateHandle.getStateFlow(DEFAULT_TRANSACTION_TYPE_ID,"")
        .combine(transactionTypes){selectedTransactionTypeId,transactionTypes->
        transactionTypes.firstOrNull { it.id==selectedTransactionTypeId }
    }.flowOn(Dispatchers.Default).stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000),null)
    fun updateDefaultTransactionType(transactionTypeId:String)=savedStateHandle.set(DEFAULT_TRANSACTION_TYPE_ID,transactionTypeId)

    private val _navigationBack:MutableStateFlow<Unit?> = MutableStateFlow(null)
    val navigationBack:StateFlow<Unit?> = _navigationBack
    fun onNavigationBack(){_navigationBack.update {  }}
    fun doneNavigationBack(){_navigationBack.update { null }}

    fun validateData()=
        name.value.isBlank().not()&&
        selectedCurrency.value!=null&&
        defaultTransactionType.value!=null

}

private val images:List<String> = listOf(
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
private fun generateImageUrl():String=images.random()
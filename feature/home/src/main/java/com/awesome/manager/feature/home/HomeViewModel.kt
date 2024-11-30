package com.awesome.manager.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.asUIState
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val homeState: HomeActions = HomeActions(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        balanceDetails = currencyRepository.returnBalanceDetails()
//            .map {
//                if (it.isNotEmpty()) AmUIState.Success(it)
//                else AmUIState.Error(AmUIError.NoDataError)
//            }
            .asUIState(viewModelScope)
    )

}
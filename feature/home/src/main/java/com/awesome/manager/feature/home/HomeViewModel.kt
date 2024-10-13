package com.awesome.manager.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.asDataStateFlow
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val homeState: HomeActions = HomeActions(
        currencyRepository.returnBalanceDetails()
            .map {
                if (it.isNotEmpty()) AmUIState.Success(it)
                else AmUIState.Error(AmUIError.NoDataError)
            }
            .asDataStateFlow(viewModelScope)
    )

}
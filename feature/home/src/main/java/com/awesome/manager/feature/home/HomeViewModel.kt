package com.awesome.manager.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val homeState: HomeMainState = HomeMainState(
        currencyRepository.returnBalanceDetails()
            .map {
                if (it.isNotEmpty()) DataState.Success(it)
                else DataState.Error
            }
            .asDataStateFlow(viewModelScope)
    )

}
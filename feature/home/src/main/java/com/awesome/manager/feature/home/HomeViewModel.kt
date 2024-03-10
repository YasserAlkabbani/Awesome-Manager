package com.awesome.manager.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val homeState: HomeState = HomeState(
        currencyRepository.returnCurrenciesBalance()
            .map { DataState.Success(it) }
            .asDataStateFlow(viewModelScope)
    )

}
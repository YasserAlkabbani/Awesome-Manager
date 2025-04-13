package com.awesome.manager.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.ui.asUIState
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val homeState: HomeState = HomeState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        balanceDetails = currencyRepository
            .returnBalanceDetails()
            .asUIState(viewModelScope)
    )

}
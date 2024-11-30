package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val authScreenState: AuthScreenState = AuthScreenState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        login = ::login,
    )


    private fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email = email, password = password).collectLatest {
                when (it) {
                    is AmUIState.Error -> when (it.amUIError) {
                        AmUIError.ConnectionUIError -> authScreenState.dynamicFabConnectionError {
                            login(email, password)
                        }
                        is AmUIError.BadRequest ->authScreenState.dynamicFabCertificationError()
                        else -> authScreenState.addError(it.amUIError)
                    }

                    is AmUIState.Loading -> authScreenState.dynamicFabLoading()
                    is AmUIState.Success -> Unit
                }
            }
        }
    }

}
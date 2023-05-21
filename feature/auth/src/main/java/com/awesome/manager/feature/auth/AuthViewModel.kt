package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
):ViewModel(){

    val authScreenState:AuthScreenState=AuthScreenState(
        savedStateHandle=savedStateHandle,login=::login, signUp =::signUp
    )

    private fun login(){
        viewModelScope.launch {
            authRepository.login(authScreenState.email.value,authScreenState.password.value).collectLatest {
                when(it){
                    is AmResult.Loading -> Timber.d("AUTH LOGIN LOADING")
                    is AmResult.Success -> Timber.d("AUTH LOGIN ${it.data}")
                    is AmResult.Error -> Timber.d("AUTH LOGIN ERROR ${it.throwable.message}")
                }
                authScreenState.updateStateBasedOnResult(it,{})
            }
        }
    }

    private fun signUp(){
        viewModelScope.launch {
            authRepository.signUp(authScreenState.email.value,authScreenState.password.value).collectLatest {
                authScreenState.updateStateBasedOnResult(it,{})
            }
        }
    }


}
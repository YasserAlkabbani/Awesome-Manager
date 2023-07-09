package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
):ViewModel(){

    val authScreenState:AuthScreenState=AuthScreenState(
        savedStateHandle=savedStateHandle,login=::login, signUp =::signUp
    )

    private fun login(){
        val email=authScreenState.email.value
        val password=authScreenState.password.value
        viewModelScope.launch {
            authRepository.login(email,password).collectLatest {
                when(it){
                    is AmResult.Loading -> Timber.d("TEST_AUTH LOGIN LOADING $email $password")
                    is AmResult.Success -> Timber.d("TEST_AUTH LOGIN SUCCESS ${it.data}")
                    is AmResult.Error -> Timber.d("TEST_AUTH LOGIN ERROR ${it.throwable.message}")
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
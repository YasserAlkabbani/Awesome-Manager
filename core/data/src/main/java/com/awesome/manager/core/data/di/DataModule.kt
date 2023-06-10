package com.awesome.manager.core.data.di

import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.accounts.OfflineFirsAccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.auth.OfflineFirstAuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.currency.OfflineCurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(authRepository: OfflineFirstAuthRepository): AuthRepository

    @Binds
    fun bindCurrencyRepository(currencyRepository: OfflineCurrencyRepository):CurrencyRepository

    @Binds
    fun bindAccountRepository(accountRepository: OfflineFirsAccountRepository):AccountRepository

}
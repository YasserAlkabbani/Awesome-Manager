package com.awesome.manager.core.data.di

import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.accounts.OfflineFirstAccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.auth.OfflineFirstAuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.currency.OfflineFirstCurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.OfflineFirstTransactionTypeRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.data.repository.transactions.OfflineFirstTransactionRepository
import com.awesome.manager.core.data.repository.transactions.TransactionRepository
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
    fun bindCurrencyRepository(currencyRepository: OfflineFirstCurrencyRepository):CurrencyRepository

    @Binds
    fun bindTransactionTypeRepository(transactionTypeRepository: OfflineFirstTransactionTypeRepository):TransactionTypeRepository

    @Binds
    fun bindAccountRepository(accountRepository: OfflineFirstAccountRepository):AccountRepository

    @Binds
    fun bindTransactionRepository(transactionRepository: OfflineFirstTransactionRepository):TransactionRepository

}
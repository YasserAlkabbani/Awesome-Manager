package com.awesome.manager.core.database.di

import com.awesome.manager.core.database.AmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaoModule{

    @Provides
    fun provideUserDao(amDatabase: AmDatabase)=amDatabase.userDao()

    @Provides
    fun provideCurrencyDao(amDatabase: AmDatabase)=amDatabase.currencyDao()

    @Provides
    fun provideTransactionType(amDatabase: AmDatabase)=amDatabase.transactionTypeDao()

    @Provides
    fun provideAccountDao(amDatabase: AmDatabase)=amDatabase.accountDao()

    @Provides
    fun provideTransactionDao(amDatabase: AmDatabase)=amDatabase.transactionDao()

}
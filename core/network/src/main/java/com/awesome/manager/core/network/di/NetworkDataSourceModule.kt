package com.awesome.manager.core.network.di

import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.datasource.TransactionTypeNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorAccountNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorAuthNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorCurrencyNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorTransactionNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorTransactionTypeNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule{

    @Binds
    fun bindAuthNetworkDataSource(authNetworkDataSource: KtorAuthNetworkDataSource): AuthNetworkDataSource

    @Binds
    fun bindCurrencyNetworkDataSource(currencyNetworkDataSource: KtorCurrencyNetworkDataSource):CurrencyNetworkDataSource

    @Binds
    fun bindTransactionTypeNetworkDataSource(transactionTypeNetworkDataSource: KtorTransactionTypeNetworkDataSource):TransactionTypeNetworkDataSource

    @Binds
    fun bindAccountNetworkDataSource(accountNetworkDataSource: KtorAccountNetworkDataSource):AccountNetworkDataSource

    @Binds
    fun bindTransactionNetworkDataSource(transactionNetworkDataSource: KtorTransactionNetworkDataSource):TransactionNetworkDataSource


}
package com.awesome.manager.core.network.di

import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorAccountNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorAuthNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorCurrencyNetworkDataSource
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
    fun bindAccountNetworkDataSource(accountNetworkDataSource: KtorAccountNetworkDataSource):AccountNetworkDataSource

}
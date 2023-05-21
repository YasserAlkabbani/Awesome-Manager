package com.awesome.manager.core.network.di

import com.awesome.manager.core.network.AuthNetworkDataSource
import com.awesome.manager.core.network.ktor.KtorAuthNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule{

    @Binds
    fun bindNetworkDataSource(authNetworkDataSource: KtorAuthNetworkDataSource):AuthNetworkDataSource

}
package com.awesome.manager.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

inline fun <T : Any, R : Any> (()->PagingSource<Int,T>).asPagingDataFlow(crossinline asModel: T.() -> R) =
    Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = this
    ).flow
        .map { it.map { it.asModel() } }
        .flowOn(Dispatchers.Default)
package com.awesome.manager.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import kotlinx.coroutines.flow.map

inline fun <T : Any, R : Any> asPagingDataFlow(noinline getPagingSource:()->PagingSource<Int,T>,crossinline asModel: T.() -> R) =
    Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = getPagingSource
    ).flow.map { it.map { it.asModel() } }
package com.awesome.manager.feature.account.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow


class AccountsMainState(
    val accounts: Flow<PagingData<AmAccount>>
) : MainState() {


}
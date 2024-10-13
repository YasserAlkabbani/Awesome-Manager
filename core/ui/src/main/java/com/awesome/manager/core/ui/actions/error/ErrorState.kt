package com.awesome.manager.core.ui.actions.error

import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.ui.actions.main.ErrorAction

interface ErrorState {

    fun ErrorAction.applyAction()

    fun removeError() = ErrorAction(AmUIError.NoError).applyAction()
    fun addError(amUIError: AmUIError) = ErrorAction(amUIError).applyAction()

}
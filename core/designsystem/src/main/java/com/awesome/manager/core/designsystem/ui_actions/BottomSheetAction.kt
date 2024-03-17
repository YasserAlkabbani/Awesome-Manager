package com.awesome.manager.core.designsystem.ui_actions

sealed class BottomSheetAction(val isOpen: Boolean) {

    abstract fun asClose(): BottomSheetAction

    data object Empty : BottomSheetAction(false) {
        override fun asClose(): BottomSheetAction = this
    }

    data class Profile(val open: Boolean) : BottomSheetAction(open) {
        override fun asClose(): BottomSheetAction = copy(open = false)
    }

    data class SearchForAccount(val open: Boolean) : BottomSheetAction(open) {
        override fun asClose(): BottomSheetAction = copy(open = false)
    }


    fun sendAction(sendMainAction: (MainActions) -> Unit) {
        sendMainAction(MainActions.BottomSheet(this))
    }

}
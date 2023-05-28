package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
class AmSheetState(
    val sheetState: SheetState,
    val isOpenButtonSheet: MutableState<Boolean>,
    val coroutineScope:CoroutineScope,
    internal val onDismiss:()->Unit,internal val onOpen:()->Unit
){
    fun close(){
        when(sheetState.currentValue){
            SheetValue.Hidden -> {}
            SheetValue.Expanded -> coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
            SheetValue.PartiallyExpanded -> coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        }
    }
    fun open(){
        when(sheetState.currentValue){
            SheetValue.Hidden -> { onOpen()}
            SheetValue.PartiallyExpanded -> { onOpen() }
            SheetValue.Expanded -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAmSheetState(
    sheetState:SheetState= rememberModalBottomSheetState(),
    coroutineScope:CoroutineScope= rememberCoroutineScope(),
    openBottomSheet: MutableState<Boolean> =  rememberSaveable { mutableStateOf(false) }
):AmSheetState{
    return remember(sheetState,coroutineScope,openBottomSheet) {
        AmSheetState(
            sheetState =sheetState,
            isOpenButtonSheet =openBottomSheet,
            coroutineScope =coroutineScope,
            onDismiss ={ openBottomSheet.value=false },
            onOpen = { openBottomSheet.value = true }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmModelBottomSheet(
   amSheetState:AmSheetState,
   content:@Composable ColumnScope.()->Unit,
) {
    if (amSheetState.isOpenButtonSheet.value) {
        ModalBottomSheet(
            onDismissRequest = amSheetState.onDismiss,
            sheetState = amSheetState.sheetState,
            content = content
        )
    }
}
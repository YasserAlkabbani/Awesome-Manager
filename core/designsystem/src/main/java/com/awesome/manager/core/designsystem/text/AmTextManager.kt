package com.awesome.manager.core.designsystem.text

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class AmTextManager(){
    data class ResourceTextManager(@StringRes val res:Int):AmTextManager()
    data class StringTextManager(val string: String):AmTextManager()
    @Composable fun asText():String=when(this){
        is ResourceTextManager -> stringResource(id = res)
        is StringTextManager -> string
    }
}

fun String?.asAmText()=AmTextManager.StringTextManager(this.orEmpty())
fun Int.asAmText()=AmTextManager.ResourceTextManager(this)
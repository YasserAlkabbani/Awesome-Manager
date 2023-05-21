package com.awesome.manager.core.designsystem.text

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class AmText(){
    data class ResourceText(@StringRes val res:Int):AmText()
    data class StringText(val string: String):AmText()
    @Composable fun asText():String=when(this){
        is ResourceText -> stringResource(id = res)
        is StringText -> string
    }
}

fun String.asAmText()=AmText.StringText(this)
fun Int.asAmText()=AmText.ResourceText(this)
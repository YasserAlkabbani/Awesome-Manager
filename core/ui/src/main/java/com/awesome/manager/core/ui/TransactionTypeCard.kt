package com.awesome.manager.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmChip


@Composable
fun TransactionType(
    modifier: Modifier=Modifier,
    type:String,
    selected:Boolean
){
    AmChip(
        modifier = modifier,
        onClick = {},
        label = type,
        selected = selected
    )
}

@Preview
@Composable
fun TransactionTypeSelectedPreview(){
    TransactionType(
        type = "TRANSACTION TYPE",
        selected = false
    )
}
@Preview
@Composable
fun TransactionTypeUnSelectedPreview(){
    TransactionType(
        type = "TRANSACTION TYPE",
        selected = true
    )
}
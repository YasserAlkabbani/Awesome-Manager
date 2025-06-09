package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding

@Composable
fun AmSpacerHeight(amSpacer: AmPadding){
    Spacer(modifier = Modifier.height(amSpacer.value))
}
@Composable
fun AmSpacerWidth(amSpacer:AmPadding){
    Spacer(modifier = Modifier.width(amSpacer.value))
}
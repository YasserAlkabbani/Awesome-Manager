package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmLinearProgress

@Composable
fun AmDynamicBarLoading(){
    AmLinearProgress(
        modifier = Modifier
            .padding(AmPadding.XX_LARGE.value)
            .height(AmSize.XX_SMALL.value)
            .width(AmSize.XXXX_LARGE.value),
    )
}
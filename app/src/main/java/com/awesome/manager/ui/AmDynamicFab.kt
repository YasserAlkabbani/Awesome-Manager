package com.awesome.manager.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmSpacerLargeWidth
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicBarLoading
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicFab
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicText
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmFabButton
import com.awesome.manager.core.ui.actions.main.DynamicFabAction
import kotlin.math.absoluteValue

@Composable
fun DynamicFabAction.Content(): Unit =
    AnimatedContent(
        targetState = this@Content,
        label = "DYNAMIC_BAR",
        transitionSpec = {
            val initDynamicFab: DynamicFabAction = initialState
            val targetDynamicFab: DynamicFabAction = targetState
            val indexDifferance = (initDynamicFab.index - targetDynamicFab.index).absoluteValue
            when (indexDifferance) {
                0 -> {
                    slideInHorizontally { width -> 0 } togetherWith
                            slideOutHorizontally { width -> 0 }
                }

                in 1..99 -> {
                    when (initDynamicFab.index > targetDynamicFab.index) {
                        true -> slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()

                        false -> slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    }
                }

                else -> {
                    when (initDynamicFab.index > targetDynamicFab.index) {
                        true -> slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()

                        false -> slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> width } + fadeOut()
                    }
                }
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) { dynamicFabAction ->
        Row(
            modifier = Modifier
                .height(AmSize.XXX_LARGE.value)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            dynamicFabAction.dynamicFabExtraButton2?.let { dynamicFabExtraButton ->
                AmDynamicFabExtraButton(dynamicFabExtraButton)
                AmSpacerLargeWidth()
            }
            dynamicFabAction.dynamicFabExtraButton1?.let { dynamicFabExtraButton ->
                AmDynamicFabExtraButton(dynamicFabExtraButton)
                AmSpacerLargeWidth()
            }
            when (dynamicFabAction) {

                DynamicFabAction.None -> {}

                DynamicFabAction.Loading -> AmDynamicBarLoading()

                is DynamicFabAction.Fab -> AmDynamicFab(dynamicFabAction.dynamicFab)

                is DynamicFabAction.Button -> AmFabButton(dynamicFabAction.dynamicFabButton)

                is DynamicFabAction.Message -> AmDynamicText(dynamicFabAction.dynamicFabText)

                is DynamicFabAction.ExtraButton -> Unit
            }
        }
    }
package com.awesome.manager.feature.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.actions.main.MainAction

@Composable
fun IntroRoute(sendMainAction: (MainAction) -> Unit) {
    IntroScreen()
}

@Composable
fun IntroScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AmCard(
            modifier = Modifier
                .padding(horizontal = (60.dp))
                .align(Alignment.Center)
                .fillMaxWidth()
                .aspectRatio(1.0f),
            shape = MaterialTheme.shapes.extraLarge,
            positive = null
        ) {
            AmIcon(
                modifier = Modifier.fillMaxSize(),
                amIconsType = AmIconsType.DrawableResourceAmIconsType(
                    com.awesome.manager.core.designsystem.R.drawable.awesome_manager_icon
                )
            )
        }
    }
}
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
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.R
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.component.surface.AmSurface

@Composable
fun IntroRoute() {
    IntroScreen()
}

@Composable
fun IntroScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AmSurface(
            modifier = Modifier
                .padding(AmPadding.XX_LARGE.value)
                .align(Alignment.Center)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            padding = AmPadding.XX_LARGE
        ) {
            AmIcon(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                amIconsType = AmIconsType.DrawableResourceAmIconsType(
                    R.drawable.awesome_manager_icon
                )
            )
        }
    }
}

@Preview
@Composable
fun IntroScreenPreview() {
    IntroScreen()
}
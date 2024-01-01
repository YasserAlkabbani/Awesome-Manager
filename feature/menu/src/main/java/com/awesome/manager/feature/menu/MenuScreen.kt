package com.awesome.manager.feature.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.core.ui.MenuItem

@Composable
fun MenuRoute() {
    MenuScreen()
}


@Composable
fun MenuScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(VERTICAL_SPACE_BETWEEN_ITEMS.dp)
    ) {
        MenuItem(
            title = "Menu 1".asAmText(),
            subTitle = "Logout And Remove Clear All Data".asAmText(),
            amIconsType = null,
            imageUrl = "https://cdn.pixabay.com/photo/2023/06/11/13/14/boathouse-8055954_1280.jpg",
            loading = true,
            onClick = {}
        )
        MenuItem(
            title = "Menu 2".asAmText(),
            subTitle = "Logout And Remove Clear All Data".asAmText(),
            amIconsType = null,
            imageUrl = "https://cdn.pixabay.com/photo/2023/04/03/18/37/nature-7897683_1280.jpg",
            loading = false,
            onClick = {}
        )
        MenuItem(
            title = "Menu 3".asAmText(), subTitle = "Logout And Remove Clear All Data".asAmText(),
            amIconsType = AmIcons.ArrowForward, imageUrl = null,
            loading = true, onClick = {}
        )
        MenuItem(
            title = "Menu 3".asAmText(), subTitle = "Logout And Remove Clear All Data".asAmText(),
            amIconsType = AmIcons.Output, imageUrl = null,
            loading = false, onClick = {}
        )
    }
}
package com.awesome.manager.navigation

import com.awesome.manager.core.designsystem.icon.Icon
import com.awesome.manager.core.designsystem.icon.MaIcon
import com.awesome.manager.feature.home.R


enum class MainDistination(
    val selectedIcon:Icon,
    val unSelectedIcon: Icon,
    val titleTextId:Int,
){

    Home(
        selectedIcon=Icon.ImageVictorIcon(MaIcon.HomeSelected),
        unSelectedIcon = Icon.ImageVictorIcon(MaIcon.HomeUnSelected),
        titleTextId = R.string.home
    )

}
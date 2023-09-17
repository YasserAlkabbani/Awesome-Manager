package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun CustomAppBar(
    startIcon:Pair<AmIconsType,()->Unit>?,
    endIcon:Pair<AmIconsType,()->Unit>?,
    title:String
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        startIcon?.let {
            AmFilledTonalIconButton(amIconsType = it.first, positive = false, onClick = it.second)
        }
        AmText(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).weight(1f),
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        endIcon?.let {
            AmFilledTonalIconButton(amIconsType = it.first, positive = false, onClick = it.second)
        }
    }
}

@Preview
@Composable
fun CustomAppBarPreview(){
    CustomAppBar(
        AmIcons.ArrowBack to {},
        AmIcons.Save to {},
        "TEST TITLE",
    )
}
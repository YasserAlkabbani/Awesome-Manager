package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmText

@Composable
fun AmTitleWithSubtitle(modifier: Modifier=Modifier,title:String,subtitle:String){
    Column(
        modifier=modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        AmText(text = title, style = MaterialTheme.typography.labelMedium)
        AmText(text = subtitle, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun AmTitleWithSubtitlePreview(){
    Surface {
        AmTitleWithSubtitle(title = "TITLE", subtitle = "SUBTITLE")
    }
}
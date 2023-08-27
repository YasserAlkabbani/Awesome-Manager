package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmSwitch(
    modifier: Modifier=Modifier,
    title:String,
    checkSubtitle:String,unCheckSubtitle:String,
    checked:Boolean,
    onCheck:(Boolean)->Unit
){
    val icon: (@Composable () -> Unit)? = if (checked) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    val subtitle:String= remember(checked) {
        if (checked)checkSubtitle else unCheckSubtitle
    }
    AmCard(
        modifier=modifier.fillMaxWidth(),
        loading=false, positive = !checked,
        onClick ={ onCheck(!checked) }
    ) {
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                AmText(text=title, style = MaterialTheme.typography.titleMedium)
                AmText(text = subtitle , style = MaterialTheme.typography.bodyMedium)
            }
            Switch(
                modifier = modifier,
                checked = checked,
                onCheckedChange = onCheck,
                thumbContent = icon
            )
        }
    }
}

@Preview
@Composable
fun AmSwitchPreview(){
    AmSwitch(
        title="TEST",
        checkSubtitle = "CHECKED",
        unCheckSubtitle = "UN_CHECKED",
        checked = true,
        onCheck = {}
    )
}
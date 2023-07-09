package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCard(modifier: Modifier,countryName:String, currencyName:String, currencySympl:String, imageUrl:String,onClick:()->Unit){
    Card(
        modifier = modifier,
        onClick = {},
        content = {
            Row(
                modifier=Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmImage(modifier = Modifier.size(40.dp), imageUrl = imageUrl)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    AmText(text = currencySympl)
                    AmText(text = "$countryName, $currencyName")
                }
            }
        },
    )
}

@Preview
@Composable
fun CurrencyCardPreview(){
    Box(Modifier.width(250.dp)) {
        CurrencyCard(modifier=Modifier.fillMaxWidth(),"Syria","Syrian Pound","SYP","", { })
    }
}
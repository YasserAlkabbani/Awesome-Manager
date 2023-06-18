package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.awesome.manager.core.designsystem.R
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmImage(modifier:Modifier,imageUrl:String){

    val placeHolderPainter= rememberVectorPainter(image = Icons.Default.Downloading)
    val errorPainter= rememberVectorPainter(image =Icons.Default.ErrorOutline)
    val painter = rememberAsyncImagePainter(
        model =imageUrl,
        placeholder = placeHolderPainter,
        error = errorPainter
    )
    AmCard(
        modifier=modifier,
        shape = CircleShape
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter=painter,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}
package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.awesome.manager.core.designsystem.R
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmImage(modifier: Modifier, imageUrl: String) {

    val placeHolderPainter = rememberVectorPainter(image = Icons.Default.Downloading)
    val errorPainter = rememberVectorPainter(image = Icons.Default.ErrorOutline)
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = placeHolderPainter,
        error = errorPainter,
        contentScale = ContentScale.Crop,

        )
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondary
    ) {
        Image(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize()
                .clip(CircleShape),
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun AmImagePreview() {
    AmImage(modifier = Modifier.size(50.dp), imageUrl = "")
}
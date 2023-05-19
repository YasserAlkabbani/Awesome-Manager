package com.awesome.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.awesome.manager.core.designsystem.theme.AwesomeManagerTheme
import com.awesome.manager.ui.AmApp
import com.awesome.manager.ui.AmAppState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainActivityViewModel:MainActivityViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            AwesomeManagerTheme {
               AmApp()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AwesomeManagerTheme {}
}
package com.awesome.manager

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.awesome.manager.core.designsystem.theme.AwesomeManagerTheme
import com.awesome.manager.ui.AmApp
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

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
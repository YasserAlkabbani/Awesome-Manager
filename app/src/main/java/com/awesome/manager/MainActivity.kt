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

        val flagIcon="https://flagicons.lipis.dev/flags/4x3/tr.svg"

        fun Currency.printValues(){
            Timber.d("TEST_CURRENCY VALUE:$this symbol:${this.symbol} currencyCode:${this.currencyCode} displayName:${this.displayName} numericCode:${this.numericCode}")
        }
        fun Locale.printValues(){
            Timber.d("TEST_CURRENCY VALUE:$this displayName:${this.displayName} name:${this.country} country:${this.displayCountry} language:${this.displayLanguage}")
        }
        val local= Locale.getDefault()
        val locals= Locale.getAvailableLocales()
        local.printValues()

        NumberFormat.getInstance().currency?.printValues()
        NumberFormat.getInstance(local).currency?.printValues()
        NumberFormat.getCurrencyInstance(local).currency?.printValues()
        NumberFormat.getCurrencyInstance().currency?.printValues()
        Currency.getInstance(local).printValues()
//        Currency.getAvailableCurrencies().map { it.printValues() }


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
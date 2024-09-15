package com.awesome.manager.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerLargeHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmPasswordTextField

@Composable
fun AuthRoute(
    sendMainAction: (MainAction) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val authScreenState = authViewModel.authScreenState
    val authData = authScreenState.authData.collectAsState().value
    val isLoading = authScreenState.isLoading.collectAsState().value
    val context = LocalContext.current

    val mainAction = authScreenState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, authScreenState::doneMainAction)
    }

    LaunchedEffect(key1 = authData, key2 = isLoading) {
        when (isLoading) {
            true -> authScreenState.dynamicBarLoading()
            false -> {
                when {
                    authData.noData -> authScreenState.dynamicBarMessage(
                        "Welcome Back", true, null
                    )

                    !authData.validateEmail -> authScreenState.dynamicBarMessage(
                        "Please use a validate email", false, null
                    )

                    !authData.validatePassword -> authScreenState.dynamicBarMessage(
                        "Please use a validate password", false, null
                    )

                    authData.validateData -> authScreenState.dynamicBarButton(
                        "Start Managing", authScreenState.login, true, null,
                    )
                }
            }
        }
    }

    AuthScreen(authScreenState)
}

@Composable
fun AuthScreen(
    authScreenState: AuthScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AmPadding.XX_LARGE.value)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(contentColor = MaterialTheme.colorScheme.secondary) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AmIcon(
                        modifier = Modifier.size(AmPadding.XX_LARGE.value * 7),
                        amIconsType = AmIcons.AwesomeManagerIcon,
                    )
                    AmText(
                        modifier = Modifier,
                        text = stringResource(R.string.awesome_manager),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
            AmSpacerLargeHeight()
            AmSpacerLargeHeight()
            AmSpacerLargeHeight()
            AmSpacerLargeHeight()
            AmSpacerLargeHeight()
            Column(Modifier.fillMaxWidth()) {
                AmCard(
                    modifier = Modifier.fillMaxWidth(), positive = null,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = AmPadding.XX_LARGE.value,
                                horizontal = AmPadding.X_SMALL.value
                            )
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            AmTextField(
                                modifier = Modifier,
                                label = stringResource(R.string.email),
                                icon = AmIcons.Email,
                                hint = "Example@Example.com",
                                onTextChange = authScreenState::updateEmail,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                ),
                                enabled = true
                            )

                            AmSpacerSmallHeight()

                            AmPasswordTextField(
                                modifier = Modifier,
                                label = stringResource(R.string.password),
                                icon = AmIcons.Password,
                                hint = "Your top secret password",
                                onTextChange = authScreenState::updatePassword,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Password
                                ),
                                enabled = true,
                            )
                        }

                    }
                }
            }
        }
    }


}

@Preview(
    device = Devices.PIXEL_7, showBackground = true
)
@Composable
fun AuthScreenPreview() {
    AuthScreen(AuthScreenState({}, {}, {}))
}
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerLargeHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.ui_actions.navigation.sendMainAction

@Composable
fun AuthRoute(
    sendMainAction: (MainAction) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val authScreenState = authViewModel.authScreenState

    val navigationAction = authScreenState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, authScreenState::resetNavigationAction)
    })

    val appBarAction = authScreenState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, authScreenState::resetAppBar)
    })

    val bottomSheetAction = authScreenState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, authScreenState::idleBottomSheet)
    })


    AuthScreen(authScreenState)
}

@Composable
fun AuthScreen(
    authScreenState: AuthScreenState
) {
    val isLoading by authScreenState.loading.collectAsState()
    val authData by authScreenState.authData.collectAsState()

    val emailErrorMessage = remember {
        derivedStateOf {
            if (authData.validateEmail || authData.email.isEmpty()) null else R.string.invalid_email
        }
    }.value?.let { stringResource(id = it) }

    val passwordErrorMessage = remember {
        derivedStateOf {
            if (authData.validatePassword || authData.password.isEmpty()) null else R.string.invalid_password
        }
    }.value?.let { stringResource(id = it) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AmPadding.EXTRA_LARGE.value)
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
                        modifier = Modifier.size(AmPadding.EXTRA_LARGE.value),
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
                AmText(
                    text = "Welcome Back !",
                    style = MaterialTheme.typography.titleLarge
                )
                AmCard(
                    modifier = Modifier.fillMaxWidth(), positive = null,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = AmPadding.EXTRA_LARGE.value,
                                horizontal = AmPadding.EXTRA_SMALL.value
                            )
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            AmTextField(
                                modifier = Modifier,
                                label = stringResource(R.string.email),
                                icon = AmIcons.Email,
                                hint = "Example@Example.com",
                                error = emailErrorMessage,
                                onTextChange = authScreenState::updateEmail,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                ),
                                enabled = !isLoading
                            )

                            AmSpacerSmallHeight()

                            AmTextField(
                                modifier = Modifier,
                                label = stringResource(R.string.password),
                                icon = AmIcons.Password,
                                hint = "Your Top Secret Password",
                                error = passwordErrorMessage,
                                onTextChange = authScreenState::updatePassword,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Password
                                ),
                                enabled = !isLoading,
                                password = true
                            )
                        }

                    }
                }
            }
        }
        AmSpacerLargeHeight()
        if (authData.validateData) {
            AmFilledTonalIconWithTextButton(
                text = stringResource(R.string.start_accounting),
                amIconsType = AmIcons.ArrowForward,
                positive = true,
                loading = isLoading,
                onClick = authScreenState.login
            )
        }
    }


}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(AuthScreenState({}, {}, {}))
}
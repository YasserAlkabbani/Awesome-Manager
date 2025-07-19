package com.awesome.manager.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AMHorizontalFloatingToolbar
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerHeight
import com.awesome.manager.core.designsystem.component.FloatingToolBarState
import com.awesome.manager.core.designsystem.component.FloatingToolbarComponent
import com.awesome.manager.core.designsystem.component.text.AmSecureTextField
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AuthRoute(
    authViewModel: AuthViewModel = hiltViewModel(),
) {

    val emailTextFieldState: TextFieldState = authViewModel.emailTextFieldState
    val passwordTextFieldState: TextFieldState = authViewModel.passwordTextFieldState

    val authState: AuthState = authViewModel.authState.collectAsStateWithLifecycle().value

    AuthScreen(
        emailTextFieldState = emailTextFieldState,
        passwordTextFieldState = passwordTextFieldState,
        authState = authState,
        login = authViewModel::login
    )
}

@Composable
fun AuthScreen(
    emailTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    authState: AuthState,
    login: () -> Unit
) {
    val floatingToolBarState: FloatingToolBarState = rememberAuthFloatingToolbarButton(
        authState = authState,
        login = login
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AmPadding.AUTH_SCREEN.value)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(contentColor = MaterialTheme.colorScheme.secondary) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AmIcon(
                        modifier = Modifier.size(AmPadding.AM_LOGO_SIZE.value),
                        amIconsType = AmIcons.AwesomeManagerIcon,
                    )
                    AmText(
                        modifier = Modifier,
                        text = stringResource(R.string.awesome_manager),
                        textStyle = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            AmSpacerHeight(AmPadding.UNDER_LOGO)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AmPadding.AUTH_TEXT_FAILED.value)
            ) {
                Column(Modifier.fillMaxWidth()) {
                    AmTextField(
                        modifier = Modifier,
                        textFieldState = emailTextFieldState,
                        placeHolder = stringResource(R.string.email),
                        icon = AmIcons.Email,
                        enabled = authState !is AuthState.Loading,
                        isValidateInput = (authState as? AuthState.ErrorInvalidInput)?.invalidEmail != true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email
                        ),
                    )

                    AmSpacerHeight(AmPadding.BETWEEN_TEXT_FILED)

                    AmSecureTextField(
                        modifier = Modifier,
                        textFieldState = passwordTextFieldState,
                        placeHolder = stringResource(R.string.password),
                        icon = AmIcons.Password,
                        enabled = authState !is AuthState.Loading,
                        isValidateInput = (authState as? AuthState.ErrorInvalidInput)?.invalidPassword != true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                    )
                }
            }
        }
        AMHorizontalFloatingToolbar(floatingToolBarState)
    }

}

@Composable
fun rememberAuthFloatingToolbarButton(
    authState: AuthState,
    login: () -> Unit,
): FloatingToolBarState = remember(authState) {
    when (authState) {
        AuthState.Loading -> FloatingToolBarState.Loading
        AuthState.InitState -> FloatingToolBarState.Content(
            textMessage = R.string.welcome_back,
        )

        is AuthState.ValidatedInput -> FloatingToolBarState.Content(
            textMessage = R.string.validated,
            actionButton = FloatingToolbarComponent.ActionButton(
                textRes = R.string.confirm,
                amIconsType = AmIcons.ArrowForward,
                onClick = login
            )
        )

        is AuthState.ErrorInvalidInput -> FloatingToolBarState.Error(
            errorMessage = R.string.invalid_email_or_password,
        )

        AuthState.ErrorRequestCertification -> FloatingToolBarState.Error(
            errorMessage = R.string.invalid_certification
        )

        AuthState.ErrorRequestConnection -> FloatingToolBarState.Error(
            errorMessage = R.string.connection_error,
            retryButton = FloatingToolbarComponent.IconButton(
                amIconsType = AmIcons.Retry,
                onClick = login
            )
        )

        AuthState.ErrorRequestUnknown -> FloatingToolBarState.Error(
            errorMessage = R.string.unknown_error,
            retryButton = FloatingToolbarComponent.IconButton(
                amIconsType = AmIcons.Retry,
                onClick = login
            )
        )

        AuthState.LoggedInSuccessfully -> FloatingToolBarState.Content(
            textMessage = R.string.logged_in_successfully
        )
    }

}

@Preview(
    device = Devices.PIXEL_9,
    showBackground = true
)
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        emailTextFieldState = TextFieldState(""),
        passwordTextFieldState = TextFieldState(""),
        authState = AuthState.InitState,
        login = {}
    )
}
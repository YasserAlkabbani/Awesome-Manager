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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSpacerLargeHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmPasswordTextField
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AuthRoute(
    sendMainAction: (MainAction) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val authScreenState = authViewModel.authScreenState

    val mainAction = authScreenState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, authScreenState::doneMainAction)
    }

    authScreenState.authUI.collectAsStateWithLifecycle(null)

    AuthScreen(authScreenState)
}

@Composable
fun AuthScreen(
    authScreenState: AuthScreenState
) {

    val email: String = authScreenState.email.collectAsState().value
    val password: String = authScreenState.password.collectAsState().value
    var passwordHidden by remember { mutableStateOf(true) }

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
                                onTextChange = authScreenState::onUpdateEmail,
                                text = email,
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
                                onTextChange = authScreenState::onUpdatePassword,
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

@Preview(
    device = Devices.PIXEL_7, showBackground = true
)
@Composable
fun AuthScreenPreview() {
    val a={MutableStateFlow("")}
    AuthScreen(
        AuthScreenState(
            {},
            {MutableStateFlow("")},
            {_,_->},
           )
    )
}
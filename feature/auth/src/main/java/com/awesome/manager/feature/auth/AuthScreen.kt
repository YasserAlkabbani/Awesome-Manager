package com.awesome.manager.feature.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmModelBottomSheet
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.component.rememberAmBottomSheetState
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmBottomSheetMessage
import com.awesome.manager.core.ui.MessageBottomData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthRoute(
    authViewModel: AuthViewModel= hiltViewModel(),
){
    val authScreenState=authViewModel.authScreenState
    val authScreenMainState=authScreenState.authScreenMainState.collectAsStateWithLifecycle().value



    val authErrorBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = authErrorBottomSheetState ) {
        AmBottomSheetMessage(
            title = stringResource(R.string.invalid_credentials),
            subtitle = stringResource(R.string.seems_your_credentials_data_is_not_correct_or_you_do_not_have_an_account_yet),
            positive = false,
            button1 = MessageBottomData(
                text = stringResource(R.string.create_a_new_account),
                positive = true,
                onClick = authScreenState.register
            ),
            button2 = MessageBottomData(
                text = stringResource(R.string.edit_credentials),
                positive = true,
                onClick = authScreenState::setMainStateAsIdle
            ),
            button3 = null, /*MessageBottomData(text="Reset Password", positive = false, onClick = authScreenState.resetPassword)*/
        )
    }
    val registerBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = registerBottomSheetState) {
        AmBottomSheetMessage(
            title = "Your Account Has Created Successfully",
            subtitle = "We Sent You A Confirmation Link\nPlease Check Your Email",
            positive = true,
            button1 = MessageBottomData(text="OK", positive = true, onClick = authScreenState::setMainStateAsIdle),
            button2 = null, button3 = null,
        )
    }
    val restPasswordBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = restPasswordBottomSheetState) {
        AmBottomSheetMessage(
            title = "Reset Password Link Has Sent Successfully",
            subtitle = "We Sent You A Reset Password Link\nPlease Check Your Email To Create Your New Password",
            positive = false,
            button1 = MessageBottomData(text="Check Email", positive = true, onClick = authScreenState.register),
            button2 = null, button3 = null,
        )
    }
    val otherErrorBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = otherErrorBottomSheetState) {
        authScreenMainState.otherError()?.let { authError->
            AmBottomSheetMessage(
                title = stringResource(R.string.there_is_something_wrong),
                subtitle = authError.errorMessage?: stringResource(R.string.there_is_unknown_error_try_again_later),
                positive = false,
                button1 = MessageBottomData(text="Ok", positive = true, onClick = authError.onDone),
                button2 = null, button3 = null,
            )
        }
    }
    val connectionErrorBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = connectionErrorBottomSheetState) {
        AmBottomSheetMessage(
            title = stringResource(R.string.connection_error),
            subtitle = stringResource(R.string.please_check_your_connection_or_try_again_later),
            positive = false,
            button1 = MessageBottomData(text= stringResource(R.string.ok), positive = true, onClick = authScreenState::setMainStateAsIdle),
            button2 = null, button3 = null,
        )
    }
    LaunchedEffect(key1 = authScreenMainState, block = {
        if (authScreenMainState is AuthScreenMainState.BottomSheetMessage){
            when(authScreenMainState.messageType){
                MessageType.LoginError -> authErrorBottomSheetState.open()
                MessageType.RegisterSuccess -> registerBottomSheetState.open()
                MessageType.ResetPasswordSuccess -> restPasswordBottomSheetState.open()
                MessageType.ConnectionError -> connectionErrorBottomSheetState.open()
                is MessageType.OtherError -> otherErrorBottomSheetState.open()
            }
        }else{
            authErrorBottomSheetState.close()
            registerBottomSheetState.close()
            restPasswordBottomSheetState.close()
            otherErrorBottomSheetState.close()
            connectionErrorBottomSheetState.close()
        }
    })

    AuthScreen(authScreenState)
}

@Composable
fun AuthScreen(
    authScreenState: AuthScreenState
){
    val email=authScreenState.email.collectAsStateWithLifecycle().value
    val emailValidate=authScreenState.validateEmail.collectAsStateWithLifecycle().value
    val emailErrorMessage= remember(email,emailValidate) {
        if (!emailValidate&&email.isNotBlank()) "Invalidate Email" else null
    }
    val password=authScreenState.password.collectAsStateWithLifecycle().value
    val passwordValidate=authScreenState.validatePassword.collectAsStateWithLifecycle().value
    val passwordErrorMessage= remember(password,passwordValidate) {
        if (!passwordValidate&&password.isNotBlank()) "Invalidate Password" else null
    }
    val validateCredentials= remember(emailErrorMessage,passwordErrorMessage) {
        emailErrorMessage==null&&passwordErrorMessage==null
    }
    val authMainState=authScreenState.authScreenMainState.collectAsStateWithLifecycle().value

    Column(
        Modifier
            .imePadding()
            .padding(6.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
                AmCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(),
                    positive = true,
                ) {
                    AmIcon(
                        modifier = Modifier.fillMaxSize(),
                        amIconsType = AmIcons.AwesomeManagerIcon
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                AmCard(
                    modifier = Modifier,
                    positive = true
                ) {
                    AmText(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        text = "Awesome\nManager", maxLines = 2,
                        style=MaterialTheme.typography.displayMedium,
                    )
                }
            }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(6.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(0.3f))

        AmSurface(
            modifier = Modifier.fillMaxWidth(),
            positive = true,loading = false,
            highPadding = true
        ) {
            Column (modifier = Modifier.fillMaxWidth()){

                AmText(
                    text = stringResource(R.string.login_register),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                AmSurface(
                    modifier = Modifier,
                    positive = null,loading = false,
                    highPadding = true,
                    content = {
                        Column (Modifier.fillMaxWidth()){
                            AmTextField(
                                modifier = Modifier,
                                label = stringResource(R.string.email), icon = AmIcons.Email, hint = "Example@Example.com",
                                text = email, error = emailErrorMessage ,onTextChange = authScreenState::updateEmail,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            AmTextField(
                                modifier = Modifier,
                                label = stringResource(R.string.password), icon = AmIcons.Password, hint = "123456789",
                                text = password, error = passwordErrorMessage ,onTextChange = authScreenState::updatePassword,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Password
                                ),
                                password = true
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        Spacer(modifier = Modifier.height(6.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(2f))

        AnimatedVisibility(visible = validateCredentials) {
            AmFilledTonalIconWithTextButton(
                text = stringResource(R.string.start_accounting), amIconsType = AmIcons.ArrowForward,
                positive = true, loading = authMainState is AuthScreenMainState.Loading,
                onClick = authScreenState.login
            )
        }

    }

}
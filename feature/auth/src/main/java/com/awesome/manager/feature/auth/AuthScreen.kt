package com.awesome.manager.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    AmModelBottomSheet(amBottomSheetState = authErrorBottomSheetState, positive = false) {
        AmBottomSheetMessage(
            title = "Invalid Credentials !!",
            subtitle = "Seems Your Credentials Data Is Not Correct, Or You Do Not Have An Account Yet .. ?",
            button1 = MessageBottomData(text="Create New Account", positive = true, onClick = authScreenState.register),
            button2 = MessageBottomData(text="Update Credentials Data", positive = true, onClick = authScreenState::setMainStateAsIdle),
            button3 = MessageBottomData(text="Reset Password", positive = false, onClick = authScreenState.resetPassword),
        )
    }
    val registerBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = registerBottomSheetState, positive = true) {
        AmBottomSheetMessage(
            title = "Your Account Has Created Successfully",
            subtitle = "We Sent You A Confirmation Link, Please Check Your Email",
            button1 = MessageBottomData(text="Create New Account", positive = true, onClick = authScreenState.register),
            button2 = MessageBottomData(text="Update Credentials Data", positive = true, onClick = authScreenState::setMainStateAsIdle),
            button3 = MessageBottomData(text="Reset Password", positive = false, onClick = authScreenState.resetPassword),
        )
    }
    val restPasswordBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = restPasswordBottomSheetState, positive = true) {
        AmBottomSheetMessage(
            title = "Reset Password Link Has Sent Successfully",
            subtitle = "We Sent You A Reset Password Link, Please Check Your Email To Create Your New Password  ..",
            button1 = MessageBottomData(text="Check Email", positive = true, onClick = authScreenState.register),
            button2 = null,
            button3 = null,
        )
    }
    val otherErrorBottomSheetState= rememberAmBottomSheetState()
    AmModelBottomSheet(amBottomSheetState = otherErrorBottomSheetState, positive = false) {
        authScreenMainState.otherError()?.let { authError->
            AmBottomSheetMessage(
                title = "Unknown Error",
                subtitle = "There Is Something Wrong ..",
                button1 = MessageBottomData(text="Try Again", positive = true, onClick = authError.onRetry),
                button2 = MessageBottomData(text="Ok", positive = false, onClick = authError.onDone),
                button3 = null,
            )
        }
    }
    LaunchedEffect(key1 = authScreenMainState, block = {
        if (authScreenMainState is AuthScreenMainState.BottomSheetMessage){
            when(authScreenMainState.messageType){
                MessageType.LoginError -> authErrorBottomSheetState.open()
                MessageType.RegisterSuccess -> registerBottomSheetState.open()
                MessageType.ResetPasswordSuccess -> restPasswordBottomSheetState.open()
                is MessageType.OtherError -> otherErrorBottomSheetState.open()
            }
        }else{
            authErrorBottomSheetState.close()
            registerBottomSheetState.close()
            restPasswordBottomSheetState.close()
            otherErrorBottomSheetState.close()
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
    val authMainState=authScreenState.authScreenMainState.collectAsStateWithLifecycle().value

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxSize(),
    ) {

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f))

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
            .weight(0.3f))

        AmSurface(
            modifier = Modifier.fillMaxWidth(),
            positive = true,loading = false
        ) {
            Column (modifier = Modifier.fillMaxWidth()){

                AmText(
                    text = "Login/Register",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                AmSurface(
                    modifier = Modifier,
                    positive = null,loading = false,
                    content = {
                        Column (Modifier.fillMaxWidth()){
                            AmTextField(
                                modifier = Modifier,
                                label = "Email", icon = AmIcons.Email, hint = "Example@Example.com",
                                text = email, error = emailErrorMessage ,onTextChange = authScreenState::updateEmail,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            AmTextField(
                                modifier = Modifier,
                                label = "Password", icon = AmIcons.Password, hint = "123456789",
                                text = password, error = passwordErrorMessage ,onTextChange = authScreenState::updatePassword,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Password
                                ),
                                visualTransformation= PasswordVisualTransformation()
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
            .weight(1.6f))

        AmFilledTonalIconWithTextButton(
            text = "Start Accounting", amIconsType = AmIcons.ArrowForward,
            positive = true, loading = authMainState is AuthScreenMainState.Loading,
            onClick = authScreenState.login
        )

    }

}
package com.awesome.manager.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmButton
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.asAmText

@Composable
fun AuthRoute(
    authViewModel: AuthViewModel= hiltViewModel(),
){
    AuthScreen(authViewModel.authScreenState)
}


@Composable
fun AuthScreen(
    authScreenState: AuthScreenState
){
    val email=authScreenState.email.collectAsStateWithLifecycle().value
    val password=authScreenState.password.collectAsStateWithLifecycle().value
    val authMainState=authScreenState.authScreenMainState.collectAsStateWithLifecycle().value

    Column(Modifier.fillMaxSize()) {
        AmIcon(amIconsType = AmIcons.AwesomeManagerIcon)
        AmCard(
            modifier = Modifier,
            content = {
                Column (modifier = Modifier.fillMaxWidth()){
                    AmText(text = "TITLE")
                    AmSurface(
                        modifier = Modifier,
                        content = {
                            Column (Modifier.fillMaxWidth()){
                                AmTextField(text = email, onTextChange = authScreenState::updateEmail)
                                AmTextField(text = password, onTextChange = authScreenState::updatePassword)
                                AmButton(text = "START ACCOUNTING", onClick = authScreenState.login)
                            }
                        }
                    )
                }
            }
        )
    }

}
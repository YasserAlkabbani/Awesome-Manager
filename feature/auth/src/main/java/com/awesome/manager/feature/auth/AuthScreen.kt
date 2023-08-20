package com.awesome.manager.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons

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

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxSize(),
    ) {

        Spacer(modifier = Modifier.fillMaxWidth().weight(0.2f))

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
                    balance = true
                ) {
                    AmIcon(
                        modifier = Modifier.fillMaxSize(),
                        amIconsType = AmIcons.AwesomeManagerIcon
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                AmCard(
                    modifier = Modifier,
                    balance = true
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

        Spacer(modifier = Modifier.fillMaxWidth().weight(0.3f))

        AmCard(
            modifier = Modifier
                .fillMaxWidth(),
            balance = true
        ) {
            Column (modifier = Modifier.fillMaxWidth()){
                AmSurface(
                    modifier = Modifier,
                    balance = null,
                    content = {
                        Column (Modifier.fillMaxWidth()){
                            AmTextField(
                                modifier = Modifier.padding(4.dp),
                                label = "Email", icon = AmIcons.Email, hint = "Example@Example.com",
                                text = email, error = null ,onTextChange = authScreenState::updateEmail
                            )
                            AmTextField(
                                modifier = Modifier.padding(4.dp),
                                label = "Password", icon = AmIcons.Password, hint = "*************",
                                text = password, error = null ,onTextChange = authScreenState::updatePassword
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))

        AmFilledTonalIconWithTextButton(text = "START ACCOUNTING", amIconsType = AmIcons.ArrowForward, isPositive = true, onClick = authScreenState.login)

    }

}
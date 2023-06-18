package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AccountEditorRoute(
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel()
) {

    val accountEditorState = accountEditorViewModel.accountEditorState


    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState: AccountEditorState) {

    val accountId = accountEditorState.accountId.collectAsStateWithLifecycle().value
    val accountName: String = accountEditorState.name.collectAsStateWithLifecycle().value
    val accountImage: String = accountEditorState.imageUrl.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AmImage(modifier = Modifier.size(75.dp), imageUrl = accountImage)
            Spacer(modifier = Modifier.width(8.dp))
            AmTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                label = "Account", icon = AmIcons.Title, hint = "Example@Example.com",
                text = accountName, onTextChange = accountEditorState::updateName
            )
        }
        AmTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Account", icon = AmIcons.Title, hint = "Example@Example.com",
            text = accountName, onTextChange = accountEditorState::updateName
        )
        AmTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Account", icon = AmIcons.Title, hint = "Example@Example.com",
            text = accountName, onTextChange = accountEditorState::updateName
        )
        AmTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Account", icon = AmIcons.Title, hint = "Example@Example.com",
            text = accountName, onTextChange = accountEditorState::updateName
        )
        AmTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Account", icon = AmIcons.Title, hint = "Example@Example.com",
            text = accountName, onTextChange = accountEditorState::updateName
        )
    }
}
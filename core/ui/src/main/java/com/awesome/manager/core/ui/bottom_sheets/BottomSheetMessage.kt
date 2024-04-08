package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmButton

data class MessageBottomData(val text: String, val positive: Boolean?, val onClick: () -> Unit)

@Composable
fun AmBottomSheetMessage(
    title: String, subtitle: String, positive: Boolean?,
    button1: MessageBottomData?,
    button2: MessageBottomData?,
    button3: MessageBottomData?,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            contentColor = when(positive){
                true -> MaterialTheme.colorScheme.primary
                false -> MaterialTheme.colorScheme.error
                null -> MaterialTheme.colorScheme.secondary
            },
            color = Color.Transparent
        ) {
            AmText(
                modifier = Modifier.wrapContentWidth(), text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.height(UIConstant.PADDING_MEDIUM.dp))
        AmText(
            modifier = Modifier.fillMaxWidth(),
            text = subtitle, style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center, maxLines = 5
        )
        Spacer(modifier = Modifier.height(UIConstant.PADDING_LARGE_EXTRA.dp))
        Spacer(modifier = Modifier.height(UIConstant.PADDING_LARGE_EXTRA.dp))
        Spacer(modifier = Modifier.height(UIConstant.PADDING_LARGE_EXTRA.dp))

        button1?.let { button ->
            AmButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = button.text,
                positive = button.positive,
                onClick = button.onClick
            )
        }
        button2?.let { button ->
            AmButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = button.text,
                positive = button.positive,
                onClick = button.onClick
            )
        }
        button3?.let { button ->
            AmButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = button.text,
                positive = button.positive,
                onClick = button.onClick
            )
        }
    }

}


@Preview
@Composable
fun AmBottomSheetMessagePreview() {
    AmBottomSheetMessage(
        title = "Title", subtitle = "SUBTITLE",
        positive = true,
        button1 = MessageBottomData(text = "BUTTON 1", positive = true, onClick = {}),
        button2 = MessageBottomData(text = "BUTTON 2", positive = false, onClick = {}),
        button3 = MessageBottomData(text = "BUTTON 3", positive = true, onClick = {})
    )
}


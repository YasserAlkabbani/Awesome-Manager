package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText
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
            contentColor = when (positive) {
                true -> MaterialTheme.colorScheme.primary
                false -> MaterialTheme.colorScheme.error
                null -> MaterialTheme.colorScheme.secondary
            },
            color = Color.Transparent
        ) {
            AmText(
                modifier = Modifier.wrapContentWidth(), text = title,
                textStyle = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.height(AmPadding.MEDIUM.value))
        AmText(
            modifier = Modifier.fillMaxWidth(),
            text = subtitle, textStyle = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center, maxLines = 5
        )
        Spacer(modifier = Modifier.height(AmPadding.XX_LARGE.value))
        Spacer(modifier = Modifier.height(AmPadding.XX_LARGE.value))
        Spacer(modifier = Modifier.height(AmPadding.XX_LARGE.value))

        button1?.let { button ->
            AmButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = button.text,
                isError = button.positive == true,
                onClick = button.onClick,
                amIconsType = null
            )
        }
        button2?.let { button ->
            AmButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = button.text,
                isError = button.positive == true,
                onClick = button.onClick,
                amIconsType = null
            )
        }
        button3?.let { button ->
            AmButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = button.text,
                isError = button.positive == true,
                onClick = button.onClick,
                amIconsType = null
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


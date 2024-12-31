package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.buttons.AmTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDatePicker(
    initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit
) {
    val state = rememberDatePickerState(initialDisplayedMonthMillis = initTime)
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                content = { AmIcon(amIconsType = AmIcons.Close) },
                onClick = dismiss
            )
            AmTextButton(
                text = "Confirm",
                enabled = state.selectedDateMillis != null,
                onClick = {
                    state.selectedDateMillis?.let {
                        setDate(it)
                        dismiss()
                    }
                }
            )
        }
        DatePicker(
            state = state,
            title = null,
            showModeToggle = false,
        )
    }
}


@Preview
@Composable
fun BottomSheetDatePackerPreview() {
    BottomSheetDatePicker(System.currentTimeMillis(), {}, {})
}

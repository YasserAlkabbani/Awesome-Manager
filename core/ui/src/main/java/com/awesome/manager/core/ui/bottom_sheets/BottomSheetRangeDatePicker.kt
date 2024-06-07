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
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.buttons.AmTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDateRangePicker(bottomSheetContent: BottomSheetContent.PickRangeDate) {
    val snackScope = rememberCoroutineScope()
    val state = rememberDateRangePickerState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                content = { AmIcon(amIconsType = AmIcons.Close) },
                onClick = bottomSheetContent.dismiss
            )
            AmTextButton(
                text = stringResource(id = R.string.confirm),
                onClick = {
                    snackScope.launch {
                        state.selectedStartDateMillis?.let { startDate ->
                            state.selectedEndDateMillis?.let { endDate ->
                                bottomSheetContent.setDate(startDate, endDate)
                                bottomSheetContent.dismiss()
                            }
                        }
                    }
                },
                enabled = state.selectedEndDateMillis != null
            )
        }
        DateRangePicker(
            state = state,
            title = null,
            showModeToggle = false,
        )
    }
}


@Preview
@Composable
fun BottomSheetDateRangePickerPreview() {
    BottomSheetDateRangePicker(bottomSheetContent = BottomSheetContent.PickRangeDate(0,
        { a, b -> },
        {}))
}
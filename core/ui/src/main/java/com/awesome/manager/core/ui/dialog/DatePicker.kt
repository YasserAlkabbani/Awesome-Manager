package com.awesome.manager.core.ui.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.component.buttons.AmTextButton
import com.awesome.manager.core.designsystem.ui_actions.picker.PickerAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmDatePickerDialog(
    pickDate: PickerAction.PickDate
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = pickDate.initTime)
    DatePickerDialog(
        onDismissRequest = pickDate.dismiss,
        confirmButton = {
            AmTextButton(
                text = "Confirm",
                onClick = {
                    datePickerState.selectedDateMillis?.let(pickDate.setDate)
                    pickDate.dismiss()
                }
            )
        },
        dismissButton = {
            AmTextButton(
                text = "Cancel",
                onClick = pickDate.dismiss
            )
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
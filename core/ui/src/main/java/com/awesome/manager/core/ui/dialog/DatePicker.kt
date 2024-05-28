package com.awesome.manager.core.ui.dialog

import android.util.Log
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.component.buttons.AmTextButton
import com.awesome.manager.core.designsystem.ui_actions.picker.PickerAction
import com.awesome.manager.core.ui.R

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
                text = stringResource(R.string.confirm),
                onClick = {
                    datePickerState.selectedDateMillis?.let(pickDate.setDate)
                    pickDate.dismiss()
                }
            )
        },
        dismissButton = {
            AmTextButton(
                text = stringResource(R.string.cancel),
                onClick = pickDate.dismiss
            )
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
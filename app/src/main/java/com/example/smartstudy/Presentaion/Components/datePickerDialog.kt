package com.example.smartstudy.Presentaion.Components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogs(
    isOpen:Boolean,
    state:DatePickerState,
    onDismiss:()->Unit,
    onConform:()->Unit
){
    if(isOpen){
        DatePickerDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
             },

            confirmButton = {
                TextButton(onClick = onConform) {
                    Text(text = "Ok")
                }
            },
            content = {
                DatePicker(
                    state = state,
                    dateValidator = {pickedDate->
                        val picked = Instant
                            .ofEpochMilli(pickedDate)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val currentDate=LocalDate.now(ZoneId.systemDefault())
                        picked>=currentDate
                    }
                )
            }
        )

    }
    
}
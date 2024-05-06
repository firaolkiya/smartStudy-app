package com.example.smartstudy.Presentaion.Components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun DeleteDialog(
    visiblity:Boolean,
    dialogTitle: String,
    dialogBody:String,
    onDismissRequest: ()->Unit,
    onConfirmation: ()->Unit
){
    if(visiblity) {
        AlertDialog(
            title = {
                Text(
                    text = dialogTitle)
            },
            text = {
                Text(text = dialogBody)
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    },
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

}
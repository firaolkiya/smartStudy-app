package com.example.smartstudy.Presentaion.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartstudy.Domain.Model.Subjects

@Composable
fun AddDialog(
    visiblity:Boolean,
    dialogTitle: String,
    getSubject:String,
    getPlannedHour:String,
    selectedColor:List<Color>,
    onColorChanged:(List<Color>)->Unit,
    onSubjectChanged: (String)->Unit,
    onPlanChanged:(String)->Unit,
    onDismissRequest: ()->Unit,
    onConfirmation: ()->Unit
){
    var subjectTypeError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var planTypeError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
     planTypeError = when{
        getSubject.isBlank()->"Please enter goal study hour"
        getPlannedHour.toFloatOrNull()==null->"Invalid number"
        getPlannedHour.toFloat()<1f->"Please enter at least 1 hour"
        getPlannedHour.toFloat()>1000f->"Goal study is too long"
        else-> null
    }
    subjectTypeError = when{
        getSubject.isBlank()->"Please enter Subject name"
        getSubject.length<=3->"Subject name is too short"
        getSubject.length>20->"Subject name is too long"
        else->null
    }

    if(visiblity) {
        AlertDialog(
            title = {
                Text(
                    text = dialogTitle)
            },
            text = {
               Column{
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ){
                    Subjects.subjectColor.forEach{ color->
                        Box (
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = if (color == selectedColor) Color.Black
                                    else Color.Transparent,
                                    shape = CircleShape
                                )
                                .background(
                                    brush = Brush.verticalGradient(color)
                                )
                                .clickable { onColorChanged(color) },
                        )
                    }
                }
                   OutlinedTextField(
                       value = getSubject,
                       onValueChange ={onSubjectChanged(it)} ,
                       label = { Text(text = "Subject") },
                       singleLine = true,
                       isError = !subjectTypeError.isNullOrBlank() and getSubject.isNotBlank(),
                       supportingText = { Text(text = subjectTypeError.orEmpty())}
                   )
                   OutlinedTextField(
                       value = getPlannedHour,
                       onValueChange ={onPlanChanged(it)} ,
                       label = { Text(text = "Goal Study hour") },
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                       singleLine = true,
                       supportingText = { Text(text = planTypeError.orEmpty())},
                       isError = !planTypeError.isNullOrBlank() and getPlannedHour.isNotBlank()
                   )
               }
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    },
                    enabled = subjectTypeError.isNullOrBlank() and planTypeError.isNullOrBlank()
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
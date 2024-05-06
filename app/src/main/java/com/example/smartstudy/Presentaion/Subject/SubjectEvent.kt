package com.example.smartstudy.Presentaion.Subject

import androidx.compose.ui.graphics.Color
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Task

sealed class SubjectEvent {

    data object UpdateSubject:SubjectEvent()

    data object DeleteSubject:SubjectEvent()
    data object DeleteSession:SubjectEvent()
    data class onTaskisCompleteClicked(val task: Task):SubjectEvent()
    data class onSubjectColorChange(val color: List<Color>):SubjectEvent()
    data class onSubjectNameChange(val name: String):SubjectEvent()
    data class onGoalSudyHourChange(val hours: String):SubjectEvent()
    data class onDeleteSessionClicked(val session:Session):SubjectEvent()

}
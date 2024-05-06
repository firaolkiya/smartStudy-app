package com.example.smartstudy.Presentaion.Dashboard

import androidx.compose.ui.graphics.Color
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Task

sealed class DashboardEvent {

    data object onSaveSubject:DashboardEvent()

    data object onDeleteSession:DashboardEvent()

    data class onDeleteSessionButtonClick(val session: Session):DashboardEvent()

    data class onSubjectIsCompleteClick(val task: Task):DashboardEvent()

    data class onSubjectColorChange(val color:List<Color>):DashboardEvent()

    data class onSubjectNameChange(val name:String):DashboardEvent()

    data class onGoalStudyChange(val goal:String):DashboardEvent()

}
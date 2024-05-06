package com.example.smartstudy.Presentaion.Dashboard

import androidx.compose.ui.graphics.Color
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Subjects

data class DashboardState(
    val totalSubjectCount:Int=0,
    val totalStudiedHour:Float=0f,
    val totalGoalHour:Float=0f,
    val totalSubjects:List<Subjects> = emptyList(),
    val subjectName:String="",
    val goalStudyHour:String="",
    val subjectCardColor:List<Color> = Subjects.subjectColor.random(),
    val  session: Session? =null
)
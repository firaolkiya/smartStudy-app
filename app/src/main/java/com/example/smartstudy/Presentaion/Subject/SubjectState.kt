package com.example.smartstudy.Presentaion.Subject

import androidx.compose.ui.graphics.Color
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.Domain.Model.Task

data class SubjectState(
    val subjectPlannedStudy:String="",
    val subjectStudiedHour:Float=0f,
    val currentSubjectId:Int?=null,
    val subjectGoalHour:String="",
    val currentSubjectName:String="",
    val subjectCardColor:List<Color> = Subjects.subjectColor.random(),
    val recentSession:List<Session> = emptyList(),
    val upcomingTask:List<Task> = emptyList(),
    val completedTask:List<Task> = emptyList(),
    val session: Session?=null,
    val isLoading:Boolean=false,
    val progress:Float=0f
)
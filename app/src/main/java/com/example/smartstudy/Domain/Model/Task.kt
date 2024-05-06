package com.example.smartstudy.Domain.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    val title: String,
    val description:String,
    val dueDate:Long,
    val priority:Int,
    val relatedSubject:String,
    val isCompleted:Boolean,
    @PrimaryKey(autoGenerate = true)
    val taskId:Int,
    val taskSubjectId:Int
){
}
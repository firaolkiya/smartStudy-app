package com.example.smartstudy.Domain.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
   @PrimaryKey(autoGenerate = true)
   val sessionID:Int,
   val relatedSubjects: String,
   val date:Long,
   val sessionSubjectId:Int,
   val duration:Int
)
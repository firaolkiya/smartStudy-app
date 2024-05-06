package com.example.smartstudy.Data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.Domain.Model.Task

@Database(entities =  [Subjects::class, Session::class, Task::class],
    version = 1, exportSchema = false)
@TypeConverters(ColorConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun subjectDAO():SubjectDAO
    abstract fun taskDAO():TaskDAO
    abstract fun sessionDAO():SessionDAO
}
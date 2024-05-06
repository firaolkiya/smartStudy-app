package com.example.smartstudy.Data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.smartstudy.Domain.Model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Upsert
    suspend fun Upsert(task: Task)

    @Query("DELETE FROM TASK WHERE taskId==:taskId")
    fun deleteTask(taskId: Int)

    @Query("DELETE FROM TASK WHERE taskSubjectId==:subjectId")
    suspend fun deleteTaskBySubjectId(subjectId:Int)

    @Query("DELETE FROM TASK WHERE taskId==:taskId")
    suspend fun getTaskById(taskId:Int)

    @Query("SELECT * FROM TASK WHERE taskSubjectId==:subjectId")
    fun getTaskForSubject(subjectId:Int): Flow<List<Task>>

    @Query("SELECT * FROM TASK")
    fun getAllTask(): Flow<List<Task>>
}
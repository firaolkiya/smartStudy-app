package com.example.smartstudy.Domain.Repository

import com.example.smartstudy.Domain.Model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun Upsert(task: Task)

    fun deleteTask(taskId: Int)

    suspend fun deleteTaskBySubjectId(subjectId:Int)

    suspend fun getTaskById(taskId:Int)

    fun getTaskForSubject(subjectId:Int): Flow<List<Task>>

    fun getAllUpcomingTask(): Flow<List<Task>>
}
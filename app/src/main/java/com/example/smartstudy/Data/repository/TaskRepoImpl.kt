package com.example.smartstudy.Data.repository

import com.example.smartstudy.Data.local.TaskDAO
import com.example.smartstudy.Domain.Model.Task
import com.example.smartstudy.Domain.Repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepoImpl  @Inject constructor(
    val taskDAO: TaskDAO
):TaskRepository {
    override suspend fun Upsert(task: Task) {
        taskDAO.Upsert(task)
    }

    override fun deleteTask(taskId: Int) {
        taskDAO.deleteTask(taskId)
    }

    override suspend fun deleteTaskBySubjectId(subjectId: Int) {
        taskDAO.deleteTaskBySubjectId(subjectId)
    }

    override suspend fun getTaskById(taskId: Int) {
        taskDAO.getTaskById(taskId)
    }

    override fun getTaskForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDAO.getTaskForSubject(subjectId)
    }

    override fun getAllUpcomingTask(): Flow<List<Task>> {
       return taskDAO.getAllTask().map { tasks->tasks.filter { it.isCompleted.not() }}
            .map { tasks-> taskSort(tasks)}
    }

    fun taskSort(tasks:List<Task>):List<Task>{
        return tasks.sortedWith (compareBy<Task>{it.dueDate}.thenByDescending { it.priority })
        }

    }



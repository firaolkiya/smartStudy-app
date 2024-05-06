package com.example.smartstudy.Domain.Repository

import com.example.smartstudy.Domain.Model.Subjects
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    suspend fun Upsert(subject: Subjects)

    fun getTotalSubjectCount(): Flow<Int>

    fun getTotalGoalHour():Flow<Float>

    suspend fun getSubjectById(subjectId:Int): Subjects

    suspend fun deleteSubject(subjectId: Int)

    fun getListOfAllSubject(): Flow<List<Subjects>>
}
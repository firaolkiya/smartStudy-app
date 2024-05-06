package com.example.smartstudy.Data.repository

import com.example.smartstudy.Data.local.SubjectDAO
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.Domain.Repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepoImp  @Inject constructor (
    val subjectDAO: SubjectDAO
):SubjectRepository {
    override suspend fun Upsert(subject: Subjects) {
        subjectDAO.Upsert(subject)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDAO.getTotalSubjectCount()
    }

    override fun getTotalGoalHour(): Flow<Float> {
       return subjectDAO.getTotalGoalHour()
    }

    override suspend fun getSubjectById(subjectId: Int): Subjects {
        return subjectDAO.getSubjectById(subjectId)
    }

    override suspend fun deleteSubject(subjectId: Int) {
        subjectDAO.deleteSubject(subjectId)
    }

    override fun getListOfAllSubject(): Flow<List<Subjects>> {
        return subjectDAO.getListOfAllSubject()
    }
}
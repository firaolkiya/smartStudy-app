package com.example.smartstudy.Data.repository

import com.example.smartstudy.Data.local.SessionDAO
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SessionRepoImpl @Inject constructor(
    val sessionDAO: SessionDAO
):SessionRepository {
    override suspend fun Insert(session: Session) {
        sessionDAO.Insert(session)
    }

    override suspend fun Delete(session: Session) {
        sessionDAO.Delete(session)
    }

    override fun getAllUpcomingSession(): Flow<List<Session>> {
        return sessionDAO.getAllSession()
    }


    override fun getRecentSessionForSubject(subjectId: Int): Flow<List<Session>> {
        return sessionDAO.getRecentSessionForSubject(subjectId)
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDAO.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationForSubject(subjectId: Int): Flow<Long> {
        return sessionDAO.getTotalSessionDurationForSubject(subjectId)
    }
}
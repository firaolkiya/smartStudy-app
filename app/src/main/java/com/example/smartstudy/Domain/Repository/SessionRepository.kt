package com.example.smartstudy.Domain.Repository

import com.example.smartstudy.Domain.Model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository{

    suspend fun Insert(session: Session)

    suspend fun Delete(session: Session)

    fun getAllUpcomingSession(): Flow<List<Session>>

    fun getRecentSessionForSubject(subjectId:Int): Flow<List<Session>>

     fun getTotalSessionDuration(): Flow<Long>

    fun getTotalSessionDurationForSubject(subjectId: Int): Flow<Long>
}
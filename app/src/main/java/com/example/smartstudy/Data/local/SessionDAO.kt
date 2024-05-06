package com.example.smartstudy.Data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartstudy.Domain.Model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDAO {

    @Insert
    suspend fun Insert(session: Session)

    @Delete
    suspend fun Delete(session: Session)

    @Query("SELECT * FROM SESSION")
    fun getAllSession(): Flow<List<Session>>

    @Query("SELECT * FROM SESSION WHERE sessionSubjectId==:subjectId")
    fun getRecentSessionForSubject(subjectId:Int):Flow<List<Session>>

    @Query("SELECT SUM(duration) FROM SESSION")
    fun getTotalSessionDuration():Flow<Long>

    @Query("SELECT SUM(duration) FROM SESSION WHERE sessionSubjectId==:subjectId")
    fun getTotalSessionDurationForSubject(subjectId: Int):Flow<Long>


}
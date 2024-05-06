package com.example.smartstudy.Data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.smartstudy.Domain.Model.Subjects
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDAO {

    @Upsert
    suspend fun Upsert(subject: Subjects)

    @Query("SELECT COUNT(*) FROM SUBJECTS")
    fun getTotalSubjectCount(): Flow<Int>

    @Query("SELECT SUM(HOURS) FROM SUBJECTS")
    fun getTotalGoalHour():Flow<Float>

    @Query("SELECT * FROM SUBJECTS WHERE subjectId==:subjectId" )
    suspend fun getSubjectById(subjectId:Int): Subjects

    @Query("DELETE FROM SUBJECTS WHERE subjectId==:subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("SELECT * FROM SUBJECTS")
    fun getListOfAllSubject():Flow<List<Subjects>>
}
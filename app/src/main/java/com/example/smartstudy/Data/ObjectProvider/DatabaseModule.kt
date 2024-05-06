package com.example.smartstudy.Data.ObjectProvider

import android.app.Application
import androidx.room.Room
import com.example.smartstudy.Data.local.AppDatabase
import com.example.smartstudy.Data.local.SessionDAO
import com.example.smartstudy.Data.local.SubjectDAO
import com.example.smartstudy.Data.local.TaskDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getDatabaseObject(
        application: Application
    ):AppDatabase{
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "smartStudy.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSubjectDAO(database:AppDatabase):SubjectDAO{
        return database.subjectDAO()
    }

    @Provides
    @Singleton
    fun provideSessionDAO(database:AppDatabase):SessionDAO{
        return database.sessionDAO()
    }
    @Provides
    @Singleton
    fun provideTaskDAO(database:AppDatabase):TaskDAO{
        return database.taskDAO()
    }


}
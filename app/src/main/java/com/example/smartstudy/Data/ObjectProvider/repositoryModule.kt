package com.example.smartstudy.Data.ObjectProvider

import com.example.smartstudy.Data.repository.SessionRepoImpl
import com.example.smartstudy.Data.repository.SubjectRepoImp
import com.example.smartstudy.Data.repository.TaskRepoImpl
import com.example.smartstudy.Domain.Repository.SessionRepository
import com.example.smartstudy.Domain.Repository.SubjectRepository
import com.example.smartstudy.Domain.Repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class repositoryModule {

    @Singleton
    @Binds
    abstract fun subjectRepoModule(
        implemented:SubjectRepoImp
    ):SubjectRepository

    @Singleton
    @Binds
    abstract fun taskRepoModule(
        implemented:TaskRepoImpl
    ):TaskRepository

    @Singleton
    @Binds
    abstract fun sessionRepoModule(
        implemented:SessionRepoImpl
    ):SessionRepository
}
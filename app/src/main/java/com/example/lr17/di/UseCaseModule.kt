package com.example.lr17.di

import com.example.lr17.domain.repository.TaskRepository
import com.example.lr17.domain.usecase.AddTaskUseCase
import com.example.lr17.domain.usecase.GetTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTasksUseCase(
        repository: TaskRepository
    ): GetTasksUseCase {
        return GetTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(
        repository: TaskRepository
    ): AddTaskUseCase {
        return AddTaskUseCase(repository)
    }
}
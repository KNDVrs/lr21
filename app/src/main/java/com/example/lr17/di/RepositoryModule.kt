package com.example.lr17.di

import com.example.lr17.data.remote.api.TaskApi
import com.example.lr17.data.repository.TaskRepositoryImpl
import com.example.lr17.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi,
        @IoDispatcher ioDispatcher: kotlinx.coroutines.CoroutineDispatcher
    ): TaskRepository {
        return TaskRepositoryImpl(api, ioDispatcher)
    }
}
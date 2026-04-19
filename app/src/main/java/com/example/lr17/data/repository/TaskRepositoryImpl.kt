package com.example.lr17.data.repository

import com.example.lr17.data.remote.api.TaskApi
import com.example.lr17.data.remote.mapper.toDomain
import com.example.lr17.data.remote.mapper.toDto
import com.example.lr17.di.IoDispatcher
import com.example.lr17.domain.model.Task
import com.example.lr17.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TaskRepository {

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        try {
            val dtos = api.getTasks()
            Result.success(dtos.map { it.toDomain() })
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addTask(task: Task): Result<Unit> = withContext(ioDispatcher) {
        try {
            println("Отправляем задачу: ${task.title}")
            api.addTask(task.toDto())
            println("Задача отправлена успешно")
            Result.success(Unit)
        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
            Result.failure(e)
        }
    }
}
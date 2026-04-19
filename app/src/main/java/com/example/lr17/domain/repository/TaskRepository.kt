package com.example.lr17.domain.repository

import com.example.lr17.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): Result<List<Task>>
    suspend fun addTask(task: Task): Result<Unit>
}
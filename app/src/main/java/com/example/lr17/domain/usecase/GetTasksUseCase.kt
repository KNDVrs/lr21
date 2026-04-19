package com.example.lr17.domain.usecase

import com.example.lr17.domain.model.Task
import com.example.lr17.domain.repository.TaskRepository

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Result<List<Task>> = repository.getTasks()
}
package com.example.lr17.domain.usecase

import com.example.lr17.domain.model.Task
import com.example.lr17.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Unit> {
        if (task.title.isBlank()) {
            return Result.failure(IllegalArgumentException("Title cannot be empty"))
        }
        return repository.addTask(task)
    }
}
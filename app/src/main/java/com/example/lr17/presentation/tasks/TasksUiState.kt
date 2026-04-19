package com.example.lr17.presentation.tasks

import com.example.lr17.domain.model.Task

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val isAdding: Boolean = false,
    val error: String? = null
)
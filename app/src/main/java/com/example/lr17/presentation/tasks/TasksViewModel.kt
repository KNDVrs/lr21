package com.example.lr17.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lr17.di.IoDispatcher
import com.example.lr17.domain.model.Task
import com.example.lr17.domain.usecase.AddTaskUseCase
import com.example.lr17.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getTasksUseCase()
                .onSuccess { tasks ->
                    _uiState.update { it.copy(tasks = tasks, isLoading = false, error = null) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                }
        }
    }
    fun addTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isAdding = true, error = null) }
            val task = Task(
                id = 0L,
                title = title,
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )
            addTaskUseCase(task)
                .onSuccess {
                    loadTasks()
                    _uiState.update { it.copy(isAdding = false) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isAdding = false, error = e.message ?: "Ошибка добавления") }
                }
        }
    }
    fun retry() = loadTasks()
}
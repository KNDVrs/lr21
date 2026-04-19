package com.example.lr17.presentation.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lr17.domain.model.Task
import com.example.lr17.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Long = savedStateHandle["taskId"] ?: -1L

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadTask()
    }

    fun loadTask() {
        if (taskId == -1L) return
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getTasks() // получаем все задачи (можно оптимизировать)
            result.onSuccess { tasks ->
                _task.value = tasks.find { it.id == taskId }
            }.onFailure {
                _task.value = null
            }
            _isLoading.value = false
        }
    }
}
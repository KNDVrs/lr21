package com.example.lr17.presentation.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lr17.domain.model.Task

@Composable
fun TasksScreen(
    viewModel: TasksViewModel,
    onTaskClick: (Task) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Ошибка: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.retry() }) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tasks) { task ->
                        Card(
                            modifier = Modifier.clickable { onTaskClick(task) }
                        ) {
                            Text(
                                text = "${task.title} — ${if (task.isCompleted) "✓" else "○"}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        var newTaskTitle by remember { mutableStateOf("") }
        OutlinedTextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("Новая задача") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isAdding
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.addTask(newTaskTitle)
                newTaskTitle = ""
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isAdding
        ) {
            if (uiState.isAdding) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Добавить")
            }
        }
    }
}
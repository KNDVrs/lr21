package com.example.lr17.presentation.taskdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun TaskDetailScreen(
    taskId: Long,
    navController: NavController,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> CircularProgressIndicator()
            task == null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Задача не найдена", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Назад")
                    }
                }
            }
            else -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ID: ${task!!.id}", style = MaterialTheme.typography.bodyMedium)
                    Text("Название: ${task!!.title}", style = MaterialTheme.typography.headlineSmall)
                    Text("Статус: ${if (task!!.isCompleted) "Выполнена" else "Не выполнена"}")
                    Text("Создана: ${task!!.createdAt}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Назад")
                    }
                }
            }
        }
    }
}
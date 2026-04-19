package com.example.lr17

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lr17.presentation.tasks.TasksScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lr17.presentation.taskdetail.TaskDetailScreen
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "tasks"
                    ) {
                        composable("tasks") {
                            TasksScreen(
                                viewModel = hiltViewModel(),
                                onTaskClick = { task ->
                                    navController.navigate("taskDetail/${task.id}")
                                }
                            )
                        }
                        composable(
                            route = "taskDetail/{taskId}",
                            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getLong("taskId") ?: -1L
                            TaskDetailScreen(
                                taskId = taskId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
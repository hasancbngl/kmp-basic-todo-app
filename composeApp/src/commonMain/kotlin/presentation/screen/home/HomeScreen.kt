package presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import domain.RequestState
import domain.Task
import domain.TaskAction
import presentation.components.ErrorScreen
import presentation.components.LoadingScreen
import presentation.components.TaskView
import presentation.screen.task.TaskScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel,navController: NavController){
    val activeTasks by viewModel.activeTaks
    val completedTasks by viewModel.completedTasks


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Home") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate("taskScreen")
                },
                shape = RoundedCornerShape(size = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "icon"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            DisplayTasks(
                modifier = Modifier.weight(1f),
                tasks = activeTasks,
                onSelect = { selectedTask ->
                    navController.navigate("taskScreen")
                },
                onFavorite = { task, isFavorite ->
                    viewModel.setAction(
                        action = TaskAction.SetFavorite(task, isFavorite)
                    )
                },
                onComplete = { task, completed ->
                    viewModel.setAction(
                        action = TaskAction.SetCompleted(task, completed)
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            DisplayTasks(
                modifier = Modifier.weight(1f),
                tasks = completedTasks,
                showActive = false,
                onComplete = { task, completed ->
                    viewModel.setAction(
                        action = TaskAction.SetCompleted(task, completed)
                    )
                },
                onDelete = { task ->
                    viewModel.setAction(
                        action = TaskAction.Delete(task)
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun DisplayTasks(
    modifier: Modifier = Modifier,
    tasks: RequestState<List<Task>>,
    showActive: Boolean = true,
    onSelect: ((Task) -> Unit)? = null,
    onFavorite: ((Task, Boolean) -> Unit)? = null,
    onComplete: ((Task, Boolean) -> Unit)? = null,
    onDelete: ((Task) -> Unit)? = null,
) {
    var showDialog by remember { mutableStateOf(false) }
    var taskToDelete: Task? by remember { mutableStateOf(null) }

    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = "Delete", fontSize = MaterialTheme.typography.titleLarge.fontSize)
            },
            text = {
                Text(
                    text = "Are you sure you want to remove '${taskToDelete!!.title}' task?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            },
            confirmButton = {
                Button(onClick = {
                    onDelete?.invoke(taskToDelete!!)
                    showDialog = false
                    taskToDelete = null
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        taskToDelete = null
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = {
                taskToDelete = null
                showDialog = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = if (showActive) "Active Tasks" else "Completed Tasks",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        tasks.DisplayResult(
            onLoading = { LoadingScreen() },
            onError = { ErrorScreen(message = it) },
            onSuccess = {
                if (it.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)) {
                        items(
                            items = it,
                            key = { task -> task.id?.toHexString().toString() }
                        ) { task ->
                            TaskView(
                                showActive = showActive,
                                task = task,
                                onSelect = { onSelect?.invoke(task) },
                                onComplete = { selectedTask, completed ->
                                    if (onComplete != null) {
                                        onComplete(selectedTask, completed)
                                    }
                                },
                                onFavorite = { selectedTask, favorite ->
                                    onFavorite?.invoke(selectedTask, favorite)
                                },
                                onDelete = { selectedTask ->
                                    taskToDelete = selectedTask
                                    showDialog = true
                                }
                            )
                        }
                    }
                } else ErrorScreen()
            }
        )
    }
}
package presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Dao
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.TaskDao
import data.TaskRepository
import domain.RequestState
import domain.Task
import domain.TaskAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

typealias MutableTasks = MutableState<RequestState<List<Task>>>
typealias Tasks = MutableState<RequestState<List<Task>>>

class HomeViewModel(
    private val repository: TaskRepository
) : ScreenModel {
    private var _activeTasks : MutableTasks = mutableStateOf(RequestState.Idle)
    val activeTaks : Tasks = _activeTasks
    private var _completedTasks : MutableTasks = mutableStateOf(RequestState.Idle)
    val completedTasks : Tasks = _completedTasks

    init {
        _activeTasks.value = RequestState.Loading
        _completedTasks.value = RequestState.Loading
        screenModelScope.launch(Dispatchers.Main) {
            delay(500)
            repository.readActiveTasks().collectLatest {
                _activeTasks.value = it
            }
        }
        screenModelScope.launch(Dispatchers.Main) {
            delay(500)
            repository.readCompletedTasks().collectLatest {
                _completedTasks.value = it
            }
        }
    }

    fun setAction(action: TaskAction){
        when (action) {
            is TaskAction.Delete -> {
                deleteTask(action.task)
            }

            is TaskAction.SetCompleted -> {
                setCompleted(action.task, action.completed)
            }

            is TaskAction.SetFavorite -> {
                setFavorite(action.task, action.isFavorite)
            }

            else -> {}
        }
    }

    private fun setCompleted(task: Task, completed: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            repository.setTaskCompleted(task.id, completed)
        }
    }

    private fun setFavorite(task: Task, isFavorite: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            repository.setTaskFavorite(task.id, isFavorite)
        }
    }

    private fun deleteTask(task: Task) {
        screenModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task.id)
        }
    }
}
package presentation.screen.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.TaskRepository
import domain.Task
import domain.TaskAction
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    fun setAction(action: TaskAction) {
        when (action) {
            is TaskAction.Add -> {
                addTask(action.task)
            }

            is TaskAction.Update -> {
                updateTask(action.task)
            }

            else -> {}
        }
    }

    private fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}
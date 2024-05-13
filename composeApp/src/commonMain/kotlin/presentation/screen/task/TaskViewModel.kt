package presentation.screen.task

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.TaskRepository
import domain.Task
import domain.TaskAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ScreenModel {
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
        screenModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    private fun updateTask(task: Task) {
        screenModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }
}
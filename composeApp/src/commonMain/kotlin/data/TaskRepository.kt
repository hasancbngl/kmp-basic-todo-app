package data

import domain.RequestState
import domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun setTaskCompleted(taskId: Int, completed: Boolean) {
        taskDao.setTaskCompleted(taskId, completed)
    }

    suspend fun setTaskFavorite(taskId: Int, favorite: Boolean) {
        taskDao.setTaskFavorite(taskId, favorite)
    }

    suspend fun deleteTask(taskId: Int) {
        try {
            taskDao.deleteTask(taskId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readActiveTasks(): Flow<RequestState<List<Task>>> {
        return taskDao.readActiveTasks()
            .map { tasks -> RequestState.Success(tasks) as RequestState<List<Task>> }
            .onStart { emit(RequestState.Loading) }
            .catch { e -> emit(RequestState.Error(e.toString())) }
    }

    fun readCompletedTasks(): Flow<RequestState<List<Task>>> {
        return taskDao.readCompletedTasks()
            .map { tasks -> RequestState.Success(tasks) as RequestState<List<Task>> }
            .onStart { emit(RequestState.Loading) }
            .catch { e -> emit(RequestState.Error("Error reading completed tasks: ${e.message}")) }
    }
}
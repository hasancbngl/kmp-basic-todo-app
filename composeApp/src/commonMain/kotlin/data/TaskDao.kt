package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import domain.RequestState
import domain.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("UPDATE tasks SET completed = :completed WHERE id = :taskId")
    suspend fun setTaskCompleted(taskId: Int, completed: Boolean)

    @Query("UPDATE tasks SET favorite = :favorite WHERE id = :taskId")
    suspend fun setTaskFavorite(taskId: Int, favorite: Boolean)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("SELECT * FROM tasks WHERE completed = 0")
    fun readActiveTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE completed = 1")
    fun readCompletedTasks(): Flow<List<Task>>
}
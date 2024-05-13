package data

import androidx.room.Database
import androidx.room.RoomDatabase
import domain.Task
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
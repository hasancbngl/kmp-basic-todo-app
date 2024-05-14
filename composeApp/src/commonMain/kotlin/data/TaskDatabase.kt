package data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import domain.Task
@Database(entities = [Task::class], version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)])
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
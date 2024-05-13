import androidx.room.Room
import androidx.room.RoomDatabase
import data.TaskDatabase
import platform.Foundation.NSHomeDirectory

actual class DatabaseBuilder  {
    actual fun createDatabaseBuilder(): RoomDatabase.Builder<TaskDatabase> {
        val dbFilePath = NSHomeDirectory() + "/tasks.db"
        return Room.databaseBuilder<TaskDatabase>(
            name = dbFilePath,
            factory =  { TaskDatabase::class.instantiateImpl() }
        )
    }
}
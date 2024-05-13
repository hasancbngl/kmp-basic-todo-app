import androidx.room.RoomDatabase
import androidx.room.Room
import data.TaskDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<TaskDatabase> {
    val dbFilePath = NSHomeDirectory() + "/tasks.db"
    return Room.databaseBuilder<TaskDatabase>(
        name = dbFilePath,
        factory =  { TaskDatabase::class.instantiateImpl() }
    )
}
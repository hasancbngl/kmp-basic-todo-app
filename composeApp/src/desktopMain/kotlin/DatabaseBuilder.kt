import androidx.room.Room
import androidx.room.RoomDatabase
import data.TaskDatabase
import java.io.File

actual class DatabaseBuilder {
    actual fun createDatabaseBuilder(): RoomDatabase.Builder<TaskDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "tasks.db")
        return Room.databaseBuilder<TaskDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
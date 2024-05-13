import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import data.TaskDatabase

actual class DatabaseBuilder(private val context: Context ) {
    val appContext = context.applicationContext
    actual val createDatabaseBuilder: RoomDatabase.Builder<TaskDatabase>
        get() = Room.databaseBuilder(appContext, TaskDatabase::class.java, "tasks.db")
}
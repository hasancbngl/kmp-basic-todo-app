import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import data.TaskDatabase
import domain.Task

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<TaskDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("tasks.db")
    return Room.databaseBuilder<TaskDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
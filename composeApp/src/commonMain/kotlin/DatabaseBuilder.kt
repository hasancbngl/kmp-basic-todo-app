import androidx.room.RoomDatabase
import data.TaskDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect class DatabaseBuilder  {
    val createDatabaseBuilder:  RoomDatabase.Builder<TaskDatabase>
}
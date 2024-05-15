package data

import getRoomDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(viewModelModule, module {
                // Define the database builder without context
                single { getDatabaseBuilder() }
                single { getRoomDatabase(get()) }
                single { get<TaskDatabase>().taskDao() }

                // Repository and ViewModels
                factory { TaskRepository(get()) }
            })
        }
    }
}
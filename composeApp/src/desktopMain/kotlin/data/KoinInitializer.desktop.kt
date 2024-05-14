package data

import getRoomDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.module

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(viewModelModule, module {
                // Define the database builder without context
                single { getDatabaseBuilder() }
                single { getRoomDatabase(get()) }
                single { get<TaskDatabase>().taskDao() }

                // Repository and ViewModels
                single { TaskRepository(get()) }
            })
        }
    }
}
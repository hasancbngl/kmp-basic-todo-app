package data

import getRoomDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual class KoinInitializer {
    actual fun init() {
        startKoin {

            modules(viewModelModule,    module {
                single { getDatabaseBuilder() }
                single { getRoomDatabase(get()) }
                single { get<TaskDatabase>().taskDao() }

                // Repository and ViewModels
                single { TaskRepository(get()) }
            })
        }
    }
}
package data

import android.content.Context
import getDatabaseBuilder
import getRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual class KoinInitializer(
    private val context: Context
) {
    actual fun init() {
        startKoin {
            modules(viewModelModule, module {
                androidContext(context)
                androidLogger()
                single { getDatabaseBuilder(androidContext()) }
                single { getRoomDatabase(get()) }
                single { get<TaskDatabase>().taskDao() }

                // Repository and ViewModels
                single { TaskRepository(get()) }
            } )
        }
    }
}
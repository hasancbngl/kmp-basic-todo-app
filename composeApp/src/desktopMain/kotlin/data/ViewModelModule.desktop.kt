package data
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual val viewModelModule: Module
    get() = module {
        factory { HomeViewModel(get()) }
        factory { TaskViewModel(get()) }
    }
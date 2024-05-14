package data

import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual val viewModelModule: Module
    get() = module {
        single { TaskViewModel(get()) }
        single { HomeViewModel(get()) }
    }
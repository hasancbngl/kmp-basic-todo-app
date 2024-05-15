package data

import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual val viewModelModule = module {
    factory { HomeViewModel(get()) }
    factory { TaskViewModel(get()) }
}
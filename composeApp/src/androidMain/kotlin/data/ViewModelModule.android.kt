package data

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::TaskViewModel)
}
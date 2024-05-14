package data

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

actual val viewModelModule = module {
    singleOf(::HomeViewModel)
    singleOf(::TaskViewModel)
}
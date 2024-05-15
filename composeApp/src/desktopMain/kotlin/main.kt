import androidx.compose.material3.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.KoinInitializer
import data.TaskRepository
import data.getDatabaseBuilder
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-todo-app",
    ) {
        val db = getRoomDatabase(getDatabaseBuilder())
        val repo = TaskRepository(db.taskDao())
        App(
            homeViewModel = HomeViewModel(repo),
            taskViewModel = TaskViewModel(repo)
        )
    }
}
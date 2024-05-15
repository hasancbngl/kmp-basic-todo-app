import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.core.context.KoinContext
import presentation.screen.home.HomeScreen
import presentation.screen.home.HomeViewModel
import presentation.screen.task.TaskScreen
import presentation.screen.task.TaskViewModel

val lightRedColor = Color(color = 0xFFF57D88)
val darkRedColor = Color(color = 0xFF77000B)

@Composable
@Preview
fun App(
    homeViewModel: HomeViewModel? = null,
    taskViewModel: TaskViewModel? = null
) {
    val lightColors = lightColorScheme(
        primary = lightRedColor,
        onPrimary = darkRedColor,
        primaryContainer = lightRedColor,
        onPrimaryContainer = darkRedColor
    )
    val darkColors = darkColorScheme(
        primary = lightRedColor,
        onPrimary = darkRedColor,
        primaryContainer = lightRedColor,
        onPrimaryContainer = darkRedColor
    )
    val colors by mutableStateOf(
        if (isSystemInDarkTheme()) darkColors else lightColors
    )


    MaterialTheme(colorScheme = colors) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "homeScreen") {
            composable("homeScreen") {
                val viewModel = koinViewModel<HomeViewModel>()
                if (getPlatform().isDesktop) {
                    if (homeViewModel != null) {
                        HomeScreen(homeViewModel, navController)
                    }
                } else HomeScreen(viewModel, navController)
            }
            composable("taskScreen") {
                val viewModel = koinViewModel<TaskViewModel>()
                if (getPlatform().isDesktop){
                    if (taskViewModel != null) {
                        TaskScreen(taskViewModel, navController, null)
                    }
                } else TaskScreen(viewModel, navController, null)
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}
package presentation.screen.task

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import domain.Task
import domain.TaskAction

const val DEFAULT_TITLE = "Enter the Title"
const val DEFAULT_DESCRIPTION = "Add some description"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    navController: NavController,
    task: Task? = null
) {
    //  val navigator = LocalNavigator.currentOrThrow
    val viewModel = viewModel<TaskViewModel>()
    var currentTitle by remember {
        mutableStateOf(task?.title ?: DEFAULT_TITLE)
    }
    var currentDescription by remember {
        mutableStateOf(task?.description ?: DEFAULT_DESCRIPTION)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicTextField(
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        ),
                        singleLine = true,
                        value = currentTitle,
                        onValueChange = { currentTitle = it }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (currentTitle.isNotEmpty() && currentDescription.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        if (task != null) {
                            viewModel.setAction(
                                action = TaskAction.Update(
                                    Task().apply {
                                        id = task.id
                                        title = currentTitle
                                        description = currentDescription
                                    }
                                )
                            )
                        } else {
                            viewModel.setAction(
                                action = TaskAction.Add(
                                    Task().apply {
                                        title = currentTitle
                                        description = currentDescription
                                    }
                                )
                            )
                        }
                        navController.navigateUp()
                    },
                    shape = RoundedCornerShape(size = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checkmark Icon"
                    )
                }
            }
        }
    ) { padding ->
        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp)
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            ),
            value = currentDescription,
            onValueChange = { description -> currentDescription = description }
        )
    }
}
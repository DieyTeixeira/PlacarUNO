package br.com.dieyteixeira.placaruno.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.UsersListScreen
import br.com.dieyteixeira.placaruno.ui.states.UsersListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.UsersListViewModel
import org.koin.androidx.compose.koinViewModel

const val usersListRoute = "usersList"


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.usersListScreen(
    onPopBackStack: () -> Unit
) {
    composable(usersListRoute) {
        val viewModel = koinViewModel<UsersListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(UsersListUiState())
        UsersListScreen(
            viewModel = viewModel,
            uiState = uiState,
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToUsersList() {
    navigate(usersListRoute)
}
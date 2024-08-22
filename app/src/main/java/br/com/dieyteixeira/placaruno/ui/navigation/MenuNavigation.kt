package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.MenuScreen
import br.com.dieyteixeira.placaruno.ui.states.MenuUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.MenuViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignInViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.UsersListViewModel
import org.koin.androidx.compose.koinViewModel

const val menuRoute = "menu"

fun NavGraphBuilder.menuScreen(
    onNavigateToPlayers: () -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToNewGame: () -> Unit,
    onNavigateToScoreboard: () -> Unit,
    onNavigateToListUsers: () -> Unit
) {
    composable(menuRoute) {
        val viewModel = koinViewModel<MenuViewModel>()
        val usersListViewModel = koinViewModel<UsersListViewModel>()
        val uiState by viewModel.uiState.collectAsState(MenuUiState())
        MenuScreen(
            uiState = uiState,
            usersListViewModel = usersListViewModel,
            onPlayersClick = onNavigateToPlayers,
            onTeamsClick = onNavigateToTeams,
            onNewGameClick = onNavigateToNewGame,
            onScoreboardClick = onNavigateToScoreboard,
            onListUsersClick = onNavigateToListUsers,
            onExitToAppClick = {
                viewModel.signOut()
            }
        )
    }
}
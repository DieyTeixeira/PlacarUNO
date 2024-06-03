package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.MenuScreen
import br.com.dieyteixeira.placaruno.ui.states.MenuUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.MenuViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.compose.koinViewModel

const val menuRoute = "menu"

fun NavGraphBuilder.menuScreen(
    onNavigateToPlayers: () -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToNewGame: () -> Unit,
    onNavigateToScoreboard: () -> Unit
) {
    composable(menuRoute) {
        val viewModel = koinViewModel<MenuViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(MenuUiState())
        MenuScreen(
            uiState = uiState,
            onPlayersClick = onNavigateToPlayers,
            onTeamsClick = onNavigateToTeams,
            onNewGameClick = onNavigateToNewGame,
            onScoreboardClick = onNavigateToScoreboard,
            onExitToAppClick = {
                viewModel.signOut()
            }
        )
    }
}

//fun NavHostController.navigateToTeams() {
//    navigate(menuRoute)
//}

fun NavHostController.navigateToNewGame() {
    navigate(menuRoute)
}

fun NavHostController.navigateToScoreboard() {
    navigate(menuRoute)
}
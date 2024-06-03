package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.screens.PlayersListScreen
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import org.koin.androidx.compose.koinViewModel

const val playersRoute = "players"

fun NavGraphBuilder.playersScreen(
    onNavigateToNewPlayerForm: () -> Unit,
    onNavigateToEditPlayerForm: (Player) -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable(playersRoute) {
        val viewModel = koinViewModel<PlayersListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(PlayersListUiState())
        PlayersListScreen(
            viewModel = viewModel,
            uiState = uiState,
            onNewPlayerClick = onNavigateToNewPlayerForm,
            onPlayerClick = onNavigateToEditPlayerForm,
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToPlayers() {
    navigate(playersRoute)
}
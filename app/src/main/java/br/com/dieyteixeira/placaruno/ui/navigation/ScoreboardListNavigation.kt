package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.ui.screens.ScoreboardListScreen
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardListViewModel
import org.koin.androidx.compose.koinViewModel

const val scoreboardRoute = "scoreboard"

fun NavGraphBuilder.scoreboardListScreen(
    onNavigateToEditGameEdit: (Game) -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable(scoreboardRoute) {
        val viewModel = koinViewModel<ScoreboardListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(ScoreboardListUiState())
        ScoreboardListScreen(
            viewModel = viewModel,
            uiState = uiState,
            onGameClick = onNavigateToEditGameEdit,
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToScoreboard() {
    navigate(scoreboardRoute)
}
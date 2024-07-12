package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.ScoreboardListScreen
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardsListUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardsListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import org.koin.androidx.compose.koinViewModel

const val scoreboardRoute = "scoreboard"

fun NavGraphBuilder.scoreboardListScreen(
    onPopBackStack: () -> Unit,
) {
    composable(scoreboardRoute) {
        val viewModel = koinViewModel<ScoreboardsListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(ScoreboardsListUiState())
        ScoreboardListScreen(
            viewModel = viewModel,
            uiState = uiState,
            onGameClick = {}, //onNavigateToEditTeamEdit
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToScoreboard() {
    navigate(scoreboardRoute)
}
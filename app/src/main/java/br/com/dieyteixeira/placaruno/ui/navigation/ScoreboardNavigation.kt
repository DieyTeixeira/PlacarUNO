package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.screens.NewGameScreen
import br.com.dieyteixeira.placaruno.ui.screens.ScoreboardScreen
import br.com.dieyteixeira.placaruno.ui.screens.TeamsListScreen
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import org.koin.androidx.compose.koinViewModel

const val scoreboardRoute = "scoreboard"

fun NavGraphBuilder.scoreboardScreen(
    onPopBackStack: () -> Unit,
) {
    composable(scoreboardRoute) {
        ScoreboardScreen(
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToScoreboard() {
    navigate(scoreboardRoute)
}
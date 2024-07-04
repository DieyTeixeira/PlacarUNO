package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.screens.NewGameScreen
import br.com.dieyteixeira.placaruno.ui.screens.TeamsListScreen
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import org.koin.androidx.compose.koinViewModel

const val newGameRoute = "newGame"

fun NavGraphBuilder.newGameScreen(
    onPopBackStack: () -> Unit,
) {
    composable(newGameRoute) {
        NewGameScreen(
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToNewGame() {
    navigate(newGameRoute)
}
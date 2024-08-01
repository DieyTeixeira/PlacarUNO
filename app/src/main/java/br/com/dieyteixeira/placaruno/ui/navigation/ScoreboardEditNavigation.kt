package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.ui.screens.ScoreboardEditScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val gameEditRoute = "gameEdit"
const val gameIdArgument = "gameId"

fun NavGraphBuilder.scoreboardEditScreen(
    onPopBackStack: () -> Unit,
) {
    composable("$gameEditRoute?$gameIdArgument={$gameIdArgument}") {backStackEntry ->
        val gameId = backStackEntry.arguments?.getString(gameIdArgument)

        val viewModel = koinViewModel<ScoreboardEditViewModel>(
            parameters = { parametersOf(gameId) })
        val uiState by viewModel.uiState
            .collectAsState()

        ScoreboardEditScreen(
            uiState = uiState,
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToNewGameEdit() {
    navigate(gameEditRoute)
}

fun NavHostController.navigateToEditGameEdit(game: Game) {
    navigate("$gameEditRoute?$gameIdArgument=${game.game_id}")
}
package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.screens.PlayersEditScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersEditViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val playerFormRoute = "playerForm"
const val playerIdArgument = "playerId"

fun NavGraphBuilder.playerFormScreen(
    onPopBackStack: () -> Unit,
) {
    composable("$playerFormRoute?$playerIdArgument={$playerIdArgument}") {
        val playerId = navArgument(playerIdArgument) {
            nullable = true
        }
        val scope = rememberCoroutineScope()
        val viewModel = koinViewModel<PlayersEditViewModel>(
            parameters = { parametersOf(playerId) })
        val uiState by viewModel.uiState.collectAsState()
        PlayersEditScreen(
            uiState = uiState,
            onSaveClick = {
                scope.launch {
                    viewModel.save()
                    onPopBackStack()
                }
            },
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToNewPlayerEdit() {
    navigate(playerFormRoute)
}

fun NavHostController.navigateToEditPlayerEdit(player: Player) {
    navigate("$playerFormRoute?$playerIdArgument=${player.id}")
}
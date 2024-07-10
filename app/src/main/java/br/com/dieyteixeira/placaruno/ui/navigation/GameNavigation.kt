package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.NewGameScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsEditViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val newGameRoute = "newGame"

fun NavGraphBuilder.newGameScreen(
    onPopBackStack: () -> Unit
) {
    composable(newGameRoute) {
        val scope = rememberCoroutineScope()

        val viewModel = koinViewModel<GameViewModel>()

        val viewModelTList = koinViewModel<TeamsListViewModel>()
        val uiStateTList by viewModelTList.uiState.collectAsState()

        val viewModelPList = koinViewModel<PlayersListViewModel>()
        val uiStatePList by viewModelPList.uiState.collectAsState()

        NewGameScreen(
            uiStateTList = uiStateTList,
            uiStatePList = uiStatePList,
            onSaveClick = {
                scope.launch {
                    viewModel.save()
                }
            },
            onBackClick = onPopBackStack,
        )
    }
}

fun NavHostController.navigateToNewGame() {
    navigate(newGameRoute)
}
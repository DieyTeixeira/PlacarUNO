package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.screens.TeamsEditScreen
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsEditViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val teamEditRoute = "teamEdit"
const val teamIdArgument = "teamId"

fun NavGraphBuilder.teamEditScreen(
    onPopBackStack: () -> Unit,
) {
    composable("$teamEditRoute?$teamIdArgument={$teamIdArgument}") {backStackEntry ->
        val teamId = backStackEntry.arguments?.getString(teamIdArgument)
        val scope = rememberCoroutineScope()

        val viewModel = koinViewModel<TeamsEditViewModel>(
            parameters = { parametersOf(teamId) })
        val uiState by viewModel.uiState
            .collectAsState()

        val viewModelList = koinViewModel<TeamsListViewModel>()
        val uiStateList by viewModelList.uiState
            .collectAsState(TeamsListUiState())

        TeamsEditScreen(
            uiState = uiState,
            uiStateList = uiStateList,
            currentPlayers = uiState.players,
            onSaveTeamClick = { selectedPlayers ->
                scope.launch {
                    viewModel.save(selectedPlayers)
                    onPopBackStack()
                }
            },
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToNewTeamEdit() {
    navigate(teamEditRoute)
}

fun NavHostController.navigateToEditTeamEdit(team: Team) {
    navigate("$teamEditRoute?$teamIdArgument=${team.team_id}")
}
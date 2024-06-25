package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.screens.TeamsEditScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsEditViewModel
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
        val uiState by viewModel.uiState.collectAsState()

        TeamsEditScreen(
            uiState = uiState,
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
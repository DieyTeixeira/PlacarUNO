package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.screens.TeamsListScreen
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import org.koin.androidx.compose.koinViewModel

const val teamsRoute = "teams"

fun NavGraphBuilder.teamsListScreen(
    onNavigateToNewTeamEdit: () -> Unit,
    onNavigateToEditTeamEdit: (Team) -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable(teamsRoute) {
        val viewModel = koinViewModel<TeamsListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(TeamsListUiState())
        TeamsListScreen(
            viewModel = viewModel,
            uiState = uiState,
            onNewTeamClick = onNavigateToNewTeamEdit,
            onTeamClick = onNavigateToEditTeamEdit,
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToTeams() {
    navigate(teamsRoute)
}
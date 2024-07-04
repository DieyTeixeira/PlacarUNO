package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team

const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(

    onNavigateToPlayers: () -> Unit,
    onNavigateToNewPlayerEdit: () -> Unit,
    onNavigateToEditPlayerEdit: (Player) -> Unit,

    onNavigateToTeams: () -> Unit,
    onNavigateToNewTeamEdit: () -> Unit,
    onNavigateToEditTeamEdit: (Team) -> Unit,

    onNavigateToNewGame: () -> Unit,

    onNavigateToScoreboard: () -> Unit,

    onPopBackStack: () -> Unit,
) {
    navigation(
        startDestination = menuRoute,
        route = homeGraphRoute
    ) {
        menuScreen(
            onNavigateToPlayers = onNavigateToPlayers,
            onNavigateToTeams = onNavigateToTeams,
            onNavigateToNewGame = onNavigateToNewGame,
            onNavigateToScoreboard = onNavigateToScoreboard,
        )
        playersListScreen(
            onNavigateToNewPlayerEdit = onNavigateToNewPlayerEdit,
            onNavigateToEditPlayerEdit = onNavigateToEditPlayerEdit,
            onPopBackStack = onPopBackStack
        )
        playerEditScreen(onPopBackStack = onPopBackStack)
        teamsListScreen(
            onNavigateToNewTeamEdit = onNavigateToNewTeamEdit,
            onNavigateToEditTeamEdit = onNavigateToEditTeamEdit,
            onPopBackStack = onPopBackStack
        )
        teamEditScreen(onPopBackStack = onPopBackStack)
        newGameScreen(onPopBackStack = onPopBackStack)
        scoreboardScreen(onPopBackStack = onPopBackStack)
    }
}

fun NavHostController.navigateToHomeGraph(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
) {
    navigate(homeGraphRoute, navOptions)
}
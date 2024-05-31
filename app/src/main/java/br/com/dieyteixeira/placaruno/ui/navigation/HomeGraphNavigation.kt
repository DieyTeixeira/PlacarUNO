package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import br.com.dieyteixeira.placaruno.models.Player

const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(
    onNavigateToNewPlayerForm: () -> Unit,
    onNavigateToEditPlayerForm: (Player) -> Unit,
    onNavigateToPlayers: () -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToNewGame: () -> Unit,
    onNavigateToScoreboard: () -> Unit,
    onPopBackStack: () -> Unit,
) {
    navigation(
        startDestination = menuRoute,
        route = homeGraphRoute
    ) {
        playersScreen(
            onNavigateToNewPlayerForm = onNavigateToNewPlayerForm,
            onNavigateToEditPlayerForm = onNavigateToEditPlayerForm,
            onPopBackStack = onPopBackStack
        )
        menuScreen(
            onNavigateToPlayers = onNavigateToPlayers,
            onNavigateToTeams = onNavigateToTeams,
            onNavigateToNewGame = onNavigateToNewGame,
            onNavigateToScoreboard = onNavigateToScoreboard,
        )
        playerFormScreen(onPopBackStack = onPopBackStack)
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
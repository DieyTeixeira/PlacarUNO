package br.com.dieyteixeira.placaruno.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team

const val homeGraphRoute = "homeGraph"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeGraph(

    onNavigateToPlayers: () -> Unit,
    onNavigateToNewPlayerEdit: () -> Unit,
    onNavigateToEditPlayerEdit: (Player) -> Unit,

    onNavigateToTeams: () -> Unit,
    onNavigateToNewTeamEdit: () -> Unit,
    onNavigateToEditTeamEdit: (Team) -> Unit,

    onNavigateToNewGame: () -> Unit,

    onNavigateToScoreboard: () -> Unit,
    onNavigateToEditGameEdit: (Game) -> Unit,

    onNavigateToEditPointsEdit: (String, Int) -> Unit,

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
        newGameScreen(
            onPopBackStack = onPopBackStack,
            onNavigateToScoreboard = onNavigateToScoreboard
        )
        scoreboardListScreen(
            onNavigateToEditGameEdit = onNavigateToEditGameEdit,
            onPopBackStack = onPopBackStack)
        scoreboardEditScreen(
            onNavigateToEditPointsEdit = onNavigateToEditPointsEdit,
            onPopBackStack = onPopBackStack
        )
        pointsScreen(onPopBackStack = onPopBackStack)
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
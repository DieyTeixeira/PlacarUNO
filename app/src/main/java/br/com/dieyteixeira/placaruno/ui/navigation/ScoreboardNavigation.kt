package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.ScoreboardScreen

const val scoreboardRoute = "scoreboard"

fun NavGraphBuilder.scoreboardScreen(
    onPopBackStack: () -> Unit,
) {
    composable(scoreboardRoute) {
        ScoreboardScreen(
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToScoreboard() {
    navigate(scoreboardRoute)
}
package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.ui.screens.PointsScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.PontuationViewModel
import com.google.android.exoplayer2.util.Log
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val pointsEditRoute = "pointsEdit"

fun NavGraphBuilder.pointsScreen(
    onPopBackStack: () -> Unit,
) {
    composable("pointsEdit/{id}/{name}/{score}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
        PointsScreen(
            id = id,
            name = name,
            score = score,
            onSaveClick = onPopBackStack,
            onBackClick = onPopBackStack
        )
    }
}

fun NavHostController.navigateToEditPointsEdit(id: String, name: String, score: Int) {
    Log.d("Navigation", "Navigating to pointsEdit with name: $name and score: $score")
    navigate("pointsEdit/$id/$name/$score")
}
//package br.com.dieyteixeira.placaruno.ui.navigation
//
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.composable
//import br.com.dieyteixeira.placaruno.models.Game
//import br.com.dieyteixeira.placaruno.models.Team
//import br.com.dieyteixeira.placaruno.ui.screens.NewGameScreen
//import br.com.dieyteixeira.placaruno.ui.screens.TeamsEditScreen
//import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
//import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
//import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsEditViewModel
//import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
//import kotlinx.coroutines.launch
//import org.koin.androidx.compose.koinViewModel
//import org.koin.core.parameter.parametersOf
//
//const val gameEditRoute = "gameEdit"
//const val gameIdArgument = "gameId"
//
//fun NavGraphBuilder.GameScreen(
//    onPopBackStack: () -> Unit,
//) {
//    composable("$gameEditRoute?$gameIdArgument={$gameIdArgument}") {backStackEntry ->
//        val gameId = backStackEntry.arguments?.getString(gameIdArgument)
//        val scope = rememberCoroutineScope()
//
//        val viewModel = koinViewModel<GameViewModel>(
//            parameters = { parametersOf(gameId) })
//        val uiState by viewModel.uiState
//            .collectAsState()
//
//        TeamsEditScreen(
//            uiState = uiState,
//            currentPlayers = uiState.players,
//            onSaveTeamClick = { selectedPlayers ->
//                scope.launch {
//                    viewModel.save(selectedPlayers)
//                    onPopBackStack()
//                }
//            },
//            onBackClick = onPopBackStack
//        )
//    }
//}
//
//fun NavHostController.navigateToEditTeamEdit(game: Game) {
//    navigate("$gameEditRoute?$gameIdArgument=${game.game_id}")
//}
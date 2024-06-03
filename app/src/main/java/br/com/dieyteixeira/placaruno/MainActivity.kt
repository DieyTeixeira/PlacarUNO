package br.com.dieyteixeira.placaruno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.dieyteixeira.placaruno.ui.navigation.authGraph
import br.com.dieyteixeira.placaruno.ui.navigation.homeGraph
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToAuthGraph
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToEditPlayerEdit
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToEditTeamEdit
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToHomeGraph
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToNewGame
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToNewPlayerEdit
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToNewTeamEdit
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToPlayers
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToScoreboard
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToSignIn
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToSignUp
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToTeams
import br.com.dieyteixeira.placaruno.ui.navigation.splashScreenRoute
import br.com.dieyteixeira.placaruno.ui.navigation.splashScreen
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.viewmodels.AppState
import br.com.dieyteixeira.placaruno.ui.viewmodels.AppViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() { // teste
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlacarUNOTheme {
                val navController = rememberNavController()
                val appViewModel = koinViewModel<AppViewModel>()
                val appState by appViewModel.state
                    .collectAsState(initial = AppState())
                LaunchedEffect(appState) {
                    if (appState.isInitLoading) {
                        return@LaunchedEffect
                    }
                    appState.user?.let {
                        navController.navigateToHomeGraph()
                    } ?: navController.navigateToAuthGraph()
                }
                NavHost(
                    navController = navController,
                    startDestination = splashScreenRoute
                ) {
                    splashScreen()
                    authGraph(
                        onNavigateToSignIn = {
                            navController.navigateToSignIn(it)
                        },
                        onNavigateToSignUp = {
                            navController.navigateToSignUp()
                        }
                    )
                    homeGraph(
                        onNavigateToNewPlayerEdit = {
                            navController.navigateToNewPlayerEdit()
                        },
                        onNavigateToEditPlayerEdit = { player ->
                            navController.navigateToEditPlayerEdit(player)
                        },
                        onNavigateToPlayers = {
                            navController.navigateToPlayers()
                        },
                        onNavigateToNewTeamEdit = {
                            navController.navigateToNewTeamEdit()
                        },
                        onNavigateToEditTeamEdit = { team ->
                            navController.navigateToEditTeamEdit(team)
                        },
                        onNavigateToTeams = {
                            navController.navigateToTeams()
                        },
                        onNavigateToNewGame = {
                            navController.navigateToNewGame()
                        },
                        onNavigateToScoreboard = {
                            navController.navigateToScoreboard()
                        },
                        onPopBackStack =  {
                        navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
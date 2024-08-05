package br.com.dieyteixeira.placaruno

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.dieyteixeira.placaruno.firebase.currentVersionCode
import br.com.dieyteixeira.placaruno.ui.navigation.authGraph
import br.com.dieyteixeira.placaruno.ui.navigation.homeGraph
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToAuthGraph
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToEditGameEdit
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToEditPlayerEdit
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToEditPointsEdit
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
import br.com.dieyteixeira.placaruno.ui.navigation.navigateToUpdateScreen
import br.com.dieyteixeira.placaruno.ui.navigation.splashScreenRoute
import br.com.dieyteixeira.placaruno.ui.navigation.splashScreen
import br.com.dieyteixeira.placaruno.ui.navigation.updateScreen
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.viewmodels.AppState
import br.com.dieyteixeira.placaruno.ui.viewmodels.AppViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.P)
class MainActivity : ComponentActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlacarUNOTheme {
                val navController = rememberNavController()
                val appViewModel = koinViewModel<AppViewModel>()
                val appState by appViewModel.state.collectAsState(initial = AppState())

                LaunchedEffect(appState) {
                    if (appState.isInitLoading) {
                        return@LaunchedEffect
                    }

                    checkForUpdate { isUpToDate ->
                        if (isUpToDate) {
                            appState.user?.let {
                                navController.navigateToHomeGraph()
                            } ?: navController.navigateToAuthGraph()
                        } else {
                            navController.navigateToUpdateScreen()
                        }
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = splashScreenRoute
                ) {
                    splashScreen()
                    updateScreen()
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
                        onNavigateToEditGameEdit = { game ->
                            navController.navigateToEditGameEdit(game)
                        },
                        onNavigateToNewGame = {
                            navController.navigateToNewGame()
                        },
                        onNavigateToScoreboard = {
                            navController.navigateToScoreboard()
                        },
                        onNavigateToEditPointsEdit = { userEmail, gameId, name, score ->
                            navController.navigateToEditPointsEdit(userEmail, gameId, name, score)
                        },
                        onPopBackStack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }

    private fun checkForUpdate(onComplete: (Boolean) -> Unit) {

        db.collection("appConfig").document("version")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val latestVersion = document.getLong("latestVersion")
                    if (latestVersion != null && latestVersion > currentVersionCode) {
                        onComplete(false) // Atualização necessária
                    } else {
                        onComplete(true) // Não necessita atualização
                    }
                } else {
                    onComplete(true) // O documento não existe, assuma atualização não necessária
                }
            }
            .addOnFailureListener {
                onComplete(true) // Em caso de falha, assuma atualização não necessária
            }
    }
}
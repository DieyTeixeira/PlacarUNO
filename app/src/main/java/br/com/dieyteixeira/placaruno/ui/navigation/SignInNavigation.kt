package br.com.dieyteixeira.placaruno.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.SignInScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignInViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.UsersListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val signInRoute: String = "signIn"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.signInScreen(
    onNavigateToSignUp: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val userViewModel = koinViewModel<UsersListViewModel>()
        val scope = rememberCoroutineScope()
        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                scope.launch {
                    userViewModel.saveSignIn(uiState.email)
                    viewModel.signIn()
                }
            },
            onSignUpClick = onNavigateToSignUp,
            onForgotPasswordClick = {
                scope.launch {
                    viewModel.sendPasswordResetEmail()
                }
            }
        )
    }
}

fun NavHostController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(signInRoute, navOptions)
}
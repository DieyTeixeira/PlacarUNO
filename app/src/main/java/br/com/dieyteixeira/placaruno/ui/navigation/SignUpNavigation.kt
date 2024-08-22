package br.com.dieyteixeira.placaruno.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.screens.SignUpScreen
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignUpViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.UsersListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val signUpRoute = "signUp"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.signUpScreen(
    onNavigationToSignIn: () -> Unit,
    onPopBackStack: () -> Unit
){
    composable(signUpRoute){
        val viewModel = koinViewModel<SignUpViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val userViewModel = koinViewModel<UsersListViewModel>()
        val scope = rememberCoroutineScope()
        val signUpIsSucessful by viewModel.signUpIsSucessful.collectAsState(false)

        SignUpScreen(
            uiState = uiState,
            onSignUpClick = {
                scope.launch {
                    viewModel.signUp()
                }
            }
        )

        if (signUpIsSucessful) {
            CustomAlertDialog(
                onDismissRequest = {},
                onConfirm = {
                    scope.launch {
                        viewModel.confirmSignUpSuccess()
                        onNavigationToSignIn()
                    }
                }
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Cadastro Realizado",
                color = Color.White
            )
        },
        text = {
            Text(
                text = "Seu cadastro foi realizado com sucesso!\n" +
                        "Por favor, faça a verificação do seu e-mail para liberar o acesso.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("OK")
            }
        },
        containerColor = Color.DarkGray,
    )
}

fun NavHostController.navigateToSignUp() {
    navigate(signUpRoute)
}
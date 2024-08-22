package br.com.dieyteixeira.placaruno.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.firebase.currentVersionName
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ClickHandler
import br.com.dieyteixeira.placaruno.ui.components.vibration
import br.com.dieyteixeira.placaruno.ui.states.SignInUiState
import br.com.dieyteixeira.placaruno.ui.states.SignUpUiState
import br.com.dieyteixeira.placaruno.ui.states.UsersListUiState
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/***** FUNÇÃO PRINCIPAL *****/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInScreen(
    uiState: SignInUiState,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    /***** VARIÁVEL *****/
    val context = LocalContext.current
    val clickHandler = remember { ClickHandler() }
    val focusManager = LocalFocusManager.current
    val textFieldModifier = Modifier
        .fillMaxWidth(0.8f)
        .padding(8.dp)
    val iconColor = if (uiState.passwordCharError != null) {
        Color.LightGray
    } else {
        Color.LightGray
    }

    val isError = uiState.error != null

    LaunchedEffect(isError) {
        if (isError) {
            vibration(context)
        }
    }

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.size(50.dp))

        /***** LOGO PLACAR UNO *****/
        Image(
            painter = painterResource(id = R.drawable.ic_logo_placar_uno),
            contentDescription = "Logo Placar UNO",
            modifier = Modifier
                .size(130.dp)
        )
        Spacer(modifier = Modifier.size(30.dp))

        /***** CAMPO USUÁRIO *****/
        OutlinedTextField(
            value = uiState.email,
            onValueChange = uiState.onEmailChange,
            textFieldModifier,
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "ícone de usuário",
                    tint = Color.White
                )
            },
            label = {
                Text(
                    "Email",
                    color = Color.LightGray
                )
            }
        )

        /***** CAMPO SENHA *****/
        OutlinedTextField(
            value = uiState.password,
            onValueChange = uiState.onPasswordChange,
            modifier = textFieldModifier,
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "ícone de senha",
                    tint = Color.White
                )
            },
            label = {
                Text("Senha", color = Color.LightGray)
            },
            trailingIcon = {
                val trailingIconModifier = Modifier.clickable {
                    uiState.onTogglePasswordVisibility()
                }
                if (uiState.isShowPassword) {
                    Icon(
                        Icons.Filled.Visibility,
                        contentDescription = "ícone de visível",
                        modifier = trailingIconModifier,
                        tint = iconColor
                    )
                } else {
                    Icon(
                        Icons.Filled.VisibilityOff,
                        contentDescription = "ícone de não visível",
                        modifier = trailingIconModifier,
                        tint = iconColor
                    )
                }
            },
            visualTransformation = if (uiState.isShowPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = uiState.passwordCharError != null
        )
        if (uiState.passwordCharError != null) {
            Text(
                text = uiState.passwordCharError,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        /***** BOTÕES *****/
        Button(
            onClick = {
                if (clickHandler.canClick()) {
                    focusManager.clearFocus()
                    onSignInClick()
                }
            },
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Entrar")
        }
        TextButton(
            onClick = {
                if (clickHandler.canClick()) {
                    onSignUpClick()
                }
            },
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Cadastrar Usuário")
        }
        TextButton(
            onClick = {
                if (clickHandler.canClick()) {
                    onForgotPasswordClick()
                }
            },
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Redefinir Senha")
        }


        Spacer(modifier = Modifier.size(15.dp))
    }

    /***** RODAPÉ *****/
    Baseboard(color = Color.White)

    /***** MENSAGEM DE ERRO *****/
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        AnimatedVisibility(
            visible = isError,
            enter = slideInVertically(
                initialOffsetY = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 400)
            ) + fadeIn(animationSpec = tween(durationMillis = 400)),
            exit = slideOutVertically(
                targetOffsetY = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 400)
            ) + fadeOut(animationSpec = tween(durationMillis = 400))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                VermelhoUno.copy(alpha = 0.0f),
                                VermelhoUno.copy(alpha = 0.7f),
                                VermelhoUno,
                                VermelhoUno,
                                VermelhoUno.copy(alpha = 0.7f),
                                VermelhoUno.copy(alpha = 0.0f)
                            )
                        )
                    )
            ) {
                val error = uiState.error ?: ""
                Text(
                    text = error,
                    color = Color.White,
                    style = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
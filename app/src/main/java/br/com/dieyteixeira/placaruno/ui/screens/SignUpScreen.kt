package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.states.SignUpUiState
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    /***** VARIÁVEIS *****/
    val focusManager = LocalFocusManager.current
    val textFieldModifier = Modifier
        .fillMaxWidth(0.8f)
        .padding(8.dp)
    val iconColor = if (uiState.passwordCharError != null) {
        Color.LightGray
    } else {
        Color.LightGray
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

        /***** CAMPO CONFIRMAÇÃO SENHA *****/
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = uiState.onConfirmPasswordChange,
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
                Text("Confirmar Senha", color = Color.LightGray)
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
            isError = uiState.passwordMismatchError != null
        )
        if (uiState.passwordMismatchError != null) {
            Text(
                text = uiState.passwordMismatchError,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        /***** BOTÃO *****/
        Button(
            onClick = {
                focusManager.clearFocus()
                onSignUpClick()
            },
            textFieldModifier,
        ) {
            Text(text = "Cadastrar")
        }

    }

    /***** RODAPÉ *****/
    Baseboard()

    /***** MENSAGEM DE ERRO *****/
    Column {
        val isError = uiState.error != null
        AnimatedVisibility(visible = isError) {
            Box(modifier = Modifier
                .background(Color.Red)
            ){
                val error = uiState.error ?: ""
                Text(
                    text = error,
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
            }

        }
    }

}

/***** VISUALIZAÇÃO CADASTRO *****/
@Preview(showBackground = true, name = "Padrão")
@Composable
fun SignUpScreenPreview1() {
    PlacarUNOTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Erro")
@Composable
fun SignUpScreenPreview2() {
    PlacarUNOTheme {
        SignUpScreen(
            uiState = SignUpUiState(
                error = "Erro"
            ),
            onSignUpClick = {}
        )
    }
}
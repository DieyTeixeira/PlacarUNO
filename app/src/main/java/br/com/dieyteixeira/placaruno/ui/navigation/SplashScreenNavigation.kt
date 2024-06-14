package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import kotlinx.coroutines.delay

const val splashScreenRoute = "splashscreen"

fun NavGraphBuilder.splashScreen() {
    composable(splashScreenRoute) {
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    val colors = listOf(
        VerdeUno,
        AzulUno,
        VermelhoUno,
        AmareloUno
    )
    var colorIndex by remember { mutableStateOf(0) }
    var loadingMessage by remember { mutableStateOf("Verificando versão do app") }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500) // muda a cor a cada segundo
            colorIndex = (colorIndex + 1) % colors.size
        }
    }

    LaunchedEffect(Unit) {
        delay(2000) // Simula a verificação da versão do app
        loadingMessage = "Autenticando o usuário"
        delay(2000) // Simula a autenticação do usuário
        loadingMessage = "Carregamento completo"
        delay(1000) // Aguarda um pouco antes de mudar de tela
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF000000)),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator(
                color = colors[colorIndex],
                strokeWidth = 7.dp, // Espessura do traço do indicador
                modifier = Modifier.size(70.dp) // Tamanho do indicador
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = loadingMessage, color = Color.White)
        }
    }
}
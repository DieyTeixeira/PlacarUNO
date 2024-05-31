package br.com.dieyteixeira.placaruno.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = CinzaEscuro,
    onSecondary = AmareloUno,
    tertiary = Color.White,
    background = Color.Black,
    onBackground = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = CinzaEscuro,
    onSecondary = VermelhoUno,
    tertiary = Color.White,
    background = Color.Black,
    onBackground = Color.White,


    surface = Color.White,
    onTertiary = Color.White,
    onSurface = Color.White,
)

@Composable
fun PlacarUNOTheme( // Define o tema PlacarUNO
    darkTheme: Boolean = isSystemInDarkTheme(), // Indica se o tema é escuro ou claro com base nas configurações do sistema
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Indica se as cores dinâmicas estão habilitadas (disponível apenas no Android 12+)
    content: @Composable () -> Unit // Conteúdo do tema
) {
    val colorScheme = when { // Define o esquema de cores com base nas configurações
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> { // Verifica se as cores dinâmicas estão habilitadas e se o dispositivo está executando Android 12+
            val context = LocalContext.current // Obtém o contexto local
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context) // Aplica esquema de cores dinâmicas de acordo com o tema escuro ou claro
        }

        darkTheme -> DarkColorScheme // Aplica o esquema de cores escuro
        else -> LightColorScheme // Aplica o esquema de cores claro
    }
    val view = LocalView.current // Obtém a visualização local
    if (!view.isInEditMode) { // Verifica se a visualização está no modo de edição
        SideEffect { // Efeito colateral para configurar a cor da barra de status com base no esquema de cores
            val window = (view.context as Activity).window // Obtém a janela da atividade
            window.statusBarColor = colorScheme.secondary.toArgb() // Define a cor da barra de status com base na cor secundária do esquema de cores
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme // Define o estilo de aparência da barra de status com base no tema escuro ou claro
        }
    }

    MaterialTheme( // Aplica o tema Material
        colorScheme = colorScheme, // Aplica o esquema de cores
        typography = Typography, // Aplica a tipografia
        content = content // Aplica o conteúdo do tema
    )
}
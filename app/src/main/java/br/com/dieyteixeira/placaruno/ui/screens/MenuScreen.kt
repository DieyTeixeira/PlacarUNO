package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.compscreens.Baseboard
import br.com.dieyteixeira.placaruno.ui.compscreens.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.compscreens.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.compscreens.Header
import br.com.dieyteixeira.placaruno.ui.states.MenuUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.Cinza
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun MenuScreen(
    uiState: MenuUiState,
    modifier: Modifier = Modifier,
    onPlayersClick: () -> Unit,
    onTeamsClick: () -> Unit,
    onNewGameClick: () -> Unit,
    onScoreboardClick: () -> Unit,
    onExitToAppClick: () -> Unit
) {

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(titleHeader = "PLACAR UNO")

        /***** BOTÕES *****/
        GenericButtonBar(
            buttons = listOf(
                null, // Posição 1 sem botão
                null, // Posição 2 sem botão
                null, // Posição 3 sem botão
                null, // Posição 4 sem botão
                ButtonInfo(
                    icon = painterResource(id = R.drawable.ic_close_br),
                    description = "Sair",
                    onClick = onExitToAppClick
                )  // Posição 5 botão Sair
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(
                text = "Bem vindo(a), ",
                style = TextStyle(
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
        Row {
            Text(
                text = "${uiState.user}",
                style = TextStyle(
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(18.dp))

        /***** CORPO DA ESTRUTURA *****/
        Column(
            modifier
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            /***** ESTRUTURA BOTÕES *****/
            ColorButton(color = VerdeUno, text = "JOGADOR", onClick = onPlayersClick)
            ColorButton(color = AzulUno, text = "EQUIPE", onClick = onTeamsClick)
            ColorButton(color = VermelhoUno, text = "NOVO JOGO", onClick = onNewGameClick)
            ColorButton(color = AmareloUno, text = "PLACAR", onClick = onScoreboardClick)
        }

        //***** RODAPÉ *****
        Baseboard()
    }
}

/***** BOTÕES *****/
@Composable
fun ColorButton(color: Color, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) // Ajustar altura do botão
            .background(
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = when (text) {
                    "JOGADOR" -> Icons.Filled.Person
                    "EQUIPE" -> Icons.Filled.Groups
                    "NOVO JOGO" -> Icons.Filled.LibraryAdd
                    "PLACAR" -> Icons.Filled.Scoreboard
                    else -> Icons.Filled.Cancel
                },
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 25.sp
            )
        }
    }
}

/***** VISUALIZAÇÃO *****/
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PlacarUNOTheme {
        MenuScreen (
            uiState = MenuUiState(),
            onPlayersClick = {},
            onTeamsClick = {},
            onNewGameClick = {},
            onScoreboardClick = {},
            onExitToAppClick = {}
        )
    }
}
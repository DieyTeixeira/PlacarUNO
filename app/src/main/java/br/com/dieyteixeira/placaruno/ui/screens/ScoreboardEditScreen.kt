package br.com.dieyteixeira.placaruno.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.components.ListPlayersGame
import br.com.dieyteixeira.placaruno.ui.components.PokerTable
import br.com.dieyteixeira.placaruno.ui.components.RotationGame
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel

/***** FUNÇÃO PRINCIPAL *****/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScoreboardEditScreen(
    onBackClick: () -> Unit = {},
    gameViewModel: GameViewModel = viewModel()
) {

    val switchState by gameViewModel.switchState.collectAsState()
    val playerCount by gameViewModel.playerCount.collectAsState()
    val teamCount by gameViewModel.teamCount.collectAsState()
    val playerTeamCount by gameViewModel.playerTeamCount.collectAsState()

    val selectedPlayers by gameViewModel.selectedPlayers.collectAsState()

    var playersTotalCount by remember {
        mutableStateOf(
            if (switchState) {
                playerTeamCount * teamCount
            } else {
                playerCount
            }
        )
    }

    LaunchedEffect(switchState, playerCount, teamCount, playerTeamCount) {
        playersTotalCount = if (switchState) {
            playerTeamCount * teamCount
        } else {
            playerCount
        }
    }

    Column (
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(
            titleHeader = "PLACAR",
            backgroundColor = AmareloUno,
            icon = painterResource(id = R.drawable.ic_g_score)
        )

        /***** BOTÕES *****/
        GenericButtonBar(
            buttons = listOf(
                ButtonInfo(
                    icon = painterResource(id = R.drawable.ic_double_arrow_left),
                    description = "Back",
                    onClick = onBackClick
                ),  // Posição 1 botão
                null, // Posição 2 sem botão
                null, // Posição 3 sem botão
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(5.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 15.dp,
                        topEnd = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(10.dp)
        ){
            ListPlayersGame(playersTotalCount = playersTotalCount)
        }
        Box (modifier = Modifier.fillMaxSize(0.95f)) {
            PokerTable(playersTotalCount = playersTotalCount, selectedPlayers = selectedPlayers)
            RotationGame()
        }
    }

    /***** RODAPÉ *****/
    Baseboard()

}
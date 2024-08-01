package br.com.dieyteixeira.placaruno.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.components.ListPlayersGame
import br.com.dieyteixeira.placaruno.ui.components.PokerTable
import br.com.dieyteixeira.placaruno.ui.components.RotationGame
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerOrTeam
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel

/***** FUNÇÃO PRINCIPAL *****/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScoreboardEditScreen(
    onBackClick: () -> Unit = {},
    uiState: ScoreboardEditUiState,
    scoreboardEditViewModel: ScoreboardEditViewModel = viewModel()
) {

    val title = uiState.title

    val verificatePlayers by scoreboardEditViewModel.verificatePlayers.collectAsState()

    val selectedPlayersOrTeams by scoreboardEditViewModel.selectedPlayersOrTeams.collectAsState()
    val selectedPlayers by scoreboardEditViewModel.selectedPlayers.collectAsState()

    val playerCount by scoreboardEditViewModel.playerCount.collectAsState()
    val teamPlayerCounts by scoreboardEditViewModel.teamPlayerCounts.collectAsState()
    val playersTotalCount = when (verificatePlayers) {
        PlayerOrTeam.PLAYERS -> playerCount
        PlayerOrTeam.TEAMS -> teamPlayerCounts.values.sum()
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

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ){
            Text(
                text = title,
                style = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = Color.DarkGray.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(
                        topStart = 15.dp,
                        bottomStart = 15.dp,
                        topEnd = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(10.dp)
        ){
            ListPlayersGame(
                playersTotalCount = playerCount,
                selectedPlayers = selectedPlayersOrTeams
            )
        }
        Box (modifier = Modifier.fillMaxSize(0.95f)) {
            PokerTable(
                playersTotalCount = playersTotalCount,

                selectedPlayers = selectedPlayers
            )
            RotationGame()
        }
    }

    /***** RODAPÉ *****/
    Baseboard(color = Color.Transparent)

}
package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerOrTeam
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel

@Composable
fun ListPlayersGame (
    playersTotalCount: Int,
    selectedPlayers: List<String>,
    scoreboardEditViewModel: ScoreboardEditViewModel = viewModel()
) {
    val points = listOf(10, 210, 15, 25, 530, 30, 10, 120)

    val teamColors = listOf(VerdeUno, AzulUno, VermelhoUno, AmareloUno)

    val verificatePlayers by scoreboardEditViewModel.verificatePlayers.collectAsState()

    val firstColumnCount = (playersTotalCount + 1) / 2
    val secondColumnCount = playersTotalCount / 2

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Primeira Coluna de Jogadores (Máx 4 linhas)
        Column(
            modifier = Modifier.weight(3f)
        ) {
            for (i in 0 until firstColumnCount) {
                val teamColor = when (verificatePlayers) {
                    PlayerOrTeam.PLAYERS -> Color.Gray
                    PlayerOrTeam.TEAMS -> teamColors[i % teamColors.size]
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                bottomStart = 8.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row{
                        Box(
                            modifier = Modifier
                                .width(15.dp)
                                .height(28.dp)
                                .background(
                                    color = teamColor,
                                    shape = RoundedCornerShape(
                                        topStart = 8.dp,
                                        bottomStart = 8.dp,
                                        topEnd = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                        )
                        Text(
                            text = selectedPlayers.getOrNull(i) ?: "",
                            style = TextStyle.Default.copy(
                                color = Color.DarkGray,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
                        )
                    }
                }
            }
        }
        // Primeira Coluna de Pontos
        Column(
            modifier = Modifier.weight(1f)
        ) {
            for (i in 0 until firstColumnCount) {
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                ) {
                    Text(
                        text = points.getOrNull(i)?.toString() ?: "0",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        // Segunda Coluna de Jogadores (Máx 4 linhas)
        Column(
            modifier = Modifier.weight(3f)
        ) {
            for (i in 0 until secondColumnCount) {
                val teamColor = when (verificatePlayers) {
                    PlayerOrTeam.PLAYERS -> Color.Gray
                    PlayerOrTeam.TEAMS -> teamColors[firstColumnCount + i % teamColors.size]
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                bottomStart = 8.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    Row{
                        Box(
                            modifier = Modifier
                                .width(15.dp)
                                .height(28.dp)
                                .background(
                                    color = teamColor,
                                    shape = RoundedCornerShape(
                                        topStart = 8.dp,
                                        bottomStart = 8.dp,
                                        topEnd = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                        )
                        Text(
                            text = selectedPlayers.getOrNull(firstColumnCount + i) ?: "",
                            style = TextStyle.Default.copy(
                                color = Color.DarkGray,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
                        )
                    }
                }
            }
        }
        // Segunda Coluna de Pontos
        Column(
            modifier = Modifier.weight(1f)
        ) {
            for (i in 0 until secondColumnCount) {
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                ) {
                    Text(
                        text = points.getOrNull(firstColumnCount + i)?.toString() ?: "0",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
                    )
                }
            }
        }
    }
}
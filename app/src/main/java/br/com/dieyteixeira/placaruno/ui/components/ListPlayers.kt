package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerOrTeam
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel
import com.google.android.exoplayer2.util.Log

@Composable
fun ListPlayersGame (
    gameIdentification: String,
    playersTotalCount: Int,
    selectedPlayers: List<String>,
//    points: Map<String, Int>,
    onPlayerClick: (String, String, Int) -> Unit,
    viewModel: ScoreboardEditViewModel = viewModel()
) {

    LaunchedEffect(gameIdentification) {
        viewModel.updateGameData(gameIdentification)
    }

    val points by viewModel.gamePoints.collectAsState(initial = emptyMap())

    val teamColors = listOf(VerdeUno, AzulUno, VermelhoUno, AmareloUno)

    val verificatePlayers by viewModel.verificatePlayers.collectAsState()

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
                val playerName = selectedPlayers.getOrNull(i) ?: ""
                val playerScore = points[playerName] ?: 0
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
                        .clickable { onPlayerClick(gameIdentification, playerName, playerScore) }
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
                            text = playerName,
                            style = TextStyle.Default.copy(
                                color = Color.DarkGray,
                                fontSize = 17.sp
                            ),
                            maxLines = 1, // Limitar a uma linha
                            overflow = TextOverflow.Ellipsis, // Adicionar "..." se o texto for cortado
                            modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 3.dp)
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
                val playerName = selectedPlayers.getOrNull(i) ?: ""
                val playerScore = points[playerName] ?: 0
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
                        .clickable { onPlayerClick(gameIdentification, playerName, playerScore) }
                ) {
                    Text(
                        text = "$playerScore",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 5.dp)
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
                val playerName = selectedPlayers.getOrNull(firstColumnCount + i) ?: ""
                val playerScore = points[playerName] ?: 0
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
                        .clickable { onPlayerClick(gameIdentification, playerName, playerScore) }
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
                            text = playerName,
                            style = TextStyle.Default.copy(
                                color = Color.DarkGray,
                                fontSize = 17.sp
                            ),
                            maxLines = 1, // Limitar a uma linha
                            overflow = TextOverflow.Ellipsis, // Adicionar "..." se o texto for cortado
                            modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 3.dp)
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
                val playerName = selectedPlayers.getOrNull(firstColumnCount + i) ?: ""
                val playerScore = points[playerName] ?: 0
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
                        .clickable { onPlayerClick(gameIdentification, playerName, playerScore) }
                ) {
                    Text(
                        text = "$playerScore",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 5.dp)
                    )
                }
            }
        }
    }
}
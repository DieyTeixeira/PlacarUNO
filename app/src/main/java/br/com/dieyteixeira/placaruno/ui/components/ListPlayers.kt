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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.models.Player

@Composable
fun ListPlayersGame (
    playersTotalCount: Int,
    selectedPlayers: List<Player>
) {

    val points = listOf(10, 210, 15, 25, 530, 30, 10, 120)

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
                    Text(
                        text = selectedPlayers.getOrNull(i)?.player_name ?: "",
                        style = TextStyle.Default.copy(
                            color = Color.DarkGray,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
                    )
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
                        text = points.getOrElse(i) { 0 }.toString(),
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
                    Text(
                        text = selectedPlayers.getOrNull(firstColumnCount + i)?.player_name ?: "",
                        style = TextStyle.Default.copy(
                            color = Color.DarkGray,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
                    )
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
                        text = points.getOrElse(firstColumnCount + i) { 0 }.toString(),
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
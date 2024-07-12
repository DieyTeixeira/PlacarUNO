package br.com.dieyteixeira.placaruno.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameListPlayers(
    uiStatePList: PlayersListUiState,
    modifier: Modifier = Modifier,
    playersCount: Int,
    gameViewModel: GameViewModel
) {
    val context = LocalContext.current

    // Variáveis
    val maxPlayerSize = playersCount
    val firstColumnCount = (playersCount + 1) / 2
    val secondColumnCount = playersCount / 2

    val selectedPlayers by gameViewModel.selectedPlayers.collectAsState()

    var snackbarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            vibration(context)
            delay(2000)
            snackbarVisible = false
        }
    }

    fun isPlayerInSelected(playerName: String): Boolean {
        return selectedPlayers.contains(playerName)
    }

    fun addPlayerToSelectedList(playerName: String) {
        val player = uiStatePList.players.find { it.player_name == playerName }
        if (player != null && !isPlayerInSelected(playerName)) {
            if (selectedPlayers.size < maxPlayerSize) {
                gameViewModel.addPlayerToSelectedList(playerName)
            } else {
                snackbarVisible = true
            }
        }
    }

    fun removePlayerFromSelectedList(playerName: String) {
        gameViewModel.removePlayerFromSelectedList(playerName)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {

        // Título "Equipes Selecionadas"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            VermelhoUno.copy(alpha = 0.9f),
                            VermelhoUno.copy(alpha = 0.65f),
                            VermelhoUno.copy(alpha = 0.65f),
                            VermelhoUno.copy(alpha = 0.9f)
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStart = 15.dp,
                        bottomStart = 0.dp,
                        topEnd = 15.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = "Jogadores Selecionados",
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 15.dp,
                        topEnd = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(top = 6.dp)
        ) {
            // Coluna esquerda
            Column(
                modifier = Modifier.weight(1f)
            ) {
                selectedPlayers.take(firstColumnCount).forEach { playerName ->
                    GameListItem(
                        text = playerName,
                        isSelected = false,
                        onClick = { removePlayerFromSelectedList(playerName) }
                    )
                }
            }

            // Coluna direita
            Column(
                modifier = Modifier.weight(1f)
            ) {
                selectedPlayers.drop(firstColumnCount).take(secondColumnCount).forEach { playerName ->
                    GameListItem(
                        text = playerName,
                        isSelected = false,
                        onClick = { removePlayerFromSelectedList(playerName) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        // Snackbar de equipe completa
        AnimatedVisibility(
            visible = snackbarVisible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 400)
            ) + fadeIn(animationSpec = tween(durationMillis = 400)),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 400)
            ) + fadeOut(animationSpec = tween(durationMillis = 400))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
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
                Text(
                    text = "Número de jogadores atingido!",
                    color = Color.White,
                    style = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        /* LISTA DE JOGADORES DISPONÍVEIS */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            VermelhoUno.copy(alpha = 0.9f),
                            VermelhoUno.copy(alpha = 0.65f),
                            VermelhoUno.copy(alpha = 0.65f),
                            VermelhoUno.copy(alpha = 0.9f)
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStart = 15.dp,
                        bottomStart = 0.dp,
                        topEnd = 15.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = "Lista de Jogadores",
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 15.dp,
                        topEnd = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(top = 6.dp)
        ) {
            items(uiStatePList.players) { player ->
                val playerName = player.player_name
                val isSelected = isPlayerInSelected(playerName)
                GameListItem(
                    text = playerName,
                    isSelected = isSelected,
                    onClick = {
                        if (isSelected) {
                            removePlayerFromSelectedList(playerName)
                        } else {
                            addPlayerToSelectedList(playerName)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun GameListItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 10.dp, end = 0.dp, top = 4.dp, bottom = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = null,
            tint = if (isSelected) Color(color = 0xFF7CB839) else Color.Transparent,
            modifier = Modifier
                .size(24.dp)
                .padding(start =3.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            style = TextStyle.Default.copy(
                fontSize = 17.sp,
                color = Color.White
            ),
            modifier = Modifier.weight(1f)
        )
    }
}
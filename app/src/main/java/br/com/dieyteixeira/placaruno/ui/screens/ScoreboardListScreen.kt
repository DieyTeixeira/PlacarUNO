package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.ClickHandler
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardListUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardListViewModel

/***** FUNÇÃO PRINCIPAL *****/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreboardListScreen(
    uiState: ScoreboardListUiState,
    modifier: Modifier = Modifier,
    onGameClick: (Game) -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: ScoreboardListViewModel
) {
    val clickHandler = remember { ClickHandler() }

    LaunchedEffect(Unit) {
        viewModel.loadGames()
    }

    var lastClickedGameIndex by remember { mutableStateOf(-1) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var gameToDelete by remember { mutableStateOf<Game?>(null) }

    Column (
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
            .clickable { lastClickedGameIndex = -1 }
    ){

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

        /***** CORPO DA ESTRUTURA *****/
        Box(modifier) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.945f)
            ) {
                itemsIndexed(uiState.games) { index, game ->
                    val showActions = index == lastClickedGameIndex
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (lastClickedGameIndex == index) {
                                        lastClickedGameIndex = -1
                                    } else {
                                        lastClickedGameIndex = index
                                    }
                                },
                                onLongClick = {
                                    onGameClick(game)
                                }
                            )
                    ) {
                        Column(
                            Modifier.padding(top = 10.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp)
                                    .background(
                                        color = Color(0xFF393F42),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(horizontal = 25.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = game.game_name,
                                        style = TextStyle.Default.copy(
                                            fontSize = 18.sp,
                                            color = Color.LightGray
                                        ),
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 14.dp)
                                    )
                                    if (showActions) {
                                        IconButton(
                                            onClick = {
                                                if (clickHandler.canClick()) {
                                                    onGameClick(game)
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit",
                                                tint = Color.White
                                            )
                                        }
                                        IconButton(onClick = {
                                            gameToDelete = game
                                            showDeleteConfirmation = true
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (showActions) {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 2.dp, start = 20.dp, end = 20.dp)
                                .heightIn(max = 300.dp) // Limitar a altura
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFA7A7A7).copy(alpha = 0.5f),
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        bottomStart = 10.dp,
                                        topEnd = 0.dp,
                                        bottomEnd = 10.dp
                                    )
                                ) // Borda para visualização
                        ) {
                            Spacer(modifier = Modifier.height(5.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 3.dp,
                                        bottom = 5.dp,
                                        start = 5.dp,
                                        end = 5.dp
                                    )
                                    .heightIn(max = 300.dp)
                            ) {

                                // LISTA DE JOGADORES
                                if (!game.game_players.isNullOrEmpty()) {
                                    itemsIndexed(game.game_players) { index, playerName ->
                                        val score = game.game_scores[playerName] ?: 0
                                        Row {
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                            ) {
                                                Text(
                                                    text = "- " + playerName,
                                                    style = TextStyle.Default.copy(
                                                        fontSize = 16.sp,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    modifier = Modifier.padding(
                                                        top = 5.dp,
                                                        start = 10.dp,
                                                        end = 10.dp
                                                    )
                                                )
                                            }
                                            // PONTUAÇÃO DOS JOGADORES
                                            Column(
                                                modifier = Modifier
                                                    .padding(
                                                        top = 5.dp,
                                                        start = 5.dp,
                                                        end = 10.dp
                                                    )
                                                    .width(60.dp)
                                                    .height(20.dp)
                                                    .background(
                                                        color = Color.Gray,
                                                        shape = RoundedCornerShape(15.dp)
                                                    )
                                                    .fillMaxHeight(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "$score",
                                                    style = TextStyle.Default.copy(
                                                        fontSize = 16.sp,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 10.dp,
                                                            end = 10.dp
                                                        )
                                                        .align(Alignment.CenterHorizontally)
                                                )
                                            }
                                        }
                                    }
                                } else {

                                    // Definindo as cores para cada equipe
                                    val teamColors =
                                        listOf(VerdeUno, AzulUno, VermelhoUno, AmareloUno)

                                    // LISTA DE EQUIPES
                                    itemsIndexed(game.game_teams) { index, teamName ->
                                        val score = game.game_scores[teamName] ?: 0
                                        val teamColor = teamColors[index % teamColors.size]
                                        Column {
                                            Row {
                                                Column(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                ) {
                                                    Text(
                                                        text = "- " + teamName,
                                                        style = TextStyle.Default.copy(
                                                            fontSize = 16.sp,
                                                            color = Color.White,
                                                            fontWeight = FontWeight.Bold
                                                        ),
                                                        modifier = Modifier.padding(
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            start = 10.dp,
                                                            end = 10.dp
                                                        )
                                                    )
                                                }
                                                // PONTUAÇÃO DAS EQUIPES
                                                Column(
                                                    modifier = Modifier
                                                        .padding(
                                                            top = 5.dp,
                                                            start = 5.dp,
                                                            end = 10.dp
                                                        )
                                                        .width(60.dp)
                                                        .height(20.dp)
                                                        .background(
                                                            color = Color.Gray,
                                                            shape = RoundedCornerShape(15.dp)
                                                        )
                                                        .fillMaxHeight(),
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "$score",
                                                        style = TextStyle.Default.copy(
                                                            fontSize = 16.sp,
                                                            color = Color.White,
                                                            fontWeight = FontWeight.Bold
                                                        ),
                                                        modifier = Modifier
                                                            .padding(
                                                                start = 10.dp,
                                                                end = 10.dp
                                                            )
                                                            .align(Alignment.CenterHorizontally)
                                                    )
                                                }
                                            }
                                            // JOGADORES DE CADA EQUIPE
                                            Row(
                                                modifier = Modifier.padding(
                                                    top = 2.dp,
                                                    bottom = 2.dp,
                                                    start = 15.dp,
                                                    end = 10.dp
                                                )
                                            ) {
                                                game.game_players_team[teamName]?.forEach { playerName ->
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(start = 5.dp)
                                                            .background(
                                                                color = teamColor,
                                                                shape = RoundedCornerShape(15.dp)
                                                            )
                                                    ) {
                                                        Text(
                                                            text = playerName,
                                                            style = TextStyle.Default.copy(
                                                                fontSize = 14.sp,
                                                                color = Color.White,
                                                                fontStyle = FontStyle.Italic
                                                            ),
                                                            modifier = Modifier
                                                                .padding(start = 5.dp, end = 5.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
    }

    if (showDeleteConfirmation) {
        Box(
            Modifier
                .background(Color.Gray.copy(alpha = 0.8f))
                .fillMaxSize()
        ) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text(text = "Confirmação de Exclusão", color = Color.White) },
                text = { Text("Tem certeza que deseja excluir esta equipe?", color = Color.White) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteGame(gameToDelete!!)
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text("Sim")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDeleteConfirmation = false }
                    ) {
                        Text("Não")
                    }
                },
                containerColor = Color.DarkGray,
            )
        }
    }

    /***** RODAPÉ *****/
    Baseboard(color = Color.Transparent)

}
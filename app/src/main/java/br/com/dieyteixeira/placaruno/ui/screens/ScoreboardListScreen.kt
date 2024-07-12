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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.components.ScoreboardsGameList
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardsListUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardsListViewModel

/***** FUNÇÃO PRINCIPAL *****/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreboardListScreen(
    uiState: ScoreboardsListUiState,
    modifier: Modifier = Modifier,
    onGameClick: (Game) -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: ScoreboardsListViewModel
) {

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
            titleHeader = "PLACAR LIST",
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
            LazyColumn(Modifier.fillMaxSize()) {
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
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFF393F42),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp)
                                            .height(30.dp), //altura da linha
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = game.game_name,
                                        style = TextStyle.Default.copy(
                                            fontSize = 15.sp,
                                            color = Color.LightGray
                                        ),
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 2.dp)
                                    )
                                    if (showActions) {
                                        IconButton(onClick = { onGameClick(game) }) {
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
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 3.dp,
                                            bottom = 5.dp,
                                            start = 5.dp,
                                            end = 5.dp
                                        )
                                        .heightIn(max = 200.dp)
                                ) {
                                    itemsIndexed(if(game.game_players.isNullOrEmpty()) game.game_teams else game.game_players) { index, gameName ->
                                        Text(
                                            text = gameName,
                                            style = TextStyle.Default.copy(
                                                fontSize = 16.sp,
                                                color = Color.White
                                            ),
                                            modifier = Modifier.padding(
                                                top = 2.dp,
                                                bottom = 2.dp,
                                                start = 10.dp,
                                                end = 10.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
//                    if (showActions) {
//                        Column(
//                            modifier = Modifier
//                                .padding(bottom = 2.dp, start = 20.dp, end = 20.dp)
//                                .heightIn(max = 200.dp) // Limitar a altura
//                                .fillMaxWidth()
//                                .background(
//                                    Color(0xFFA7A7A7).copy(alpha = 0.5f),
//                                    RoundedCornerShape(
//                                        topStart = 0.dp,
//                                        bottomStart = 10.dp,
//                                        topEnd = 0.dp,
//                                        bottomEnd = 10.dp
//                                    )
//                                ) // Borda para visualização
//                        ) {
//                            Spacer(modifier = Modifier.height(3.dp))
//                            LazyColumn(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(
//                                        top = 3.dp,
//                                        bottom = 5.dp,
//                                        start = 5.dp,
//                                        end = 5.dp
//                                    )
//                                    .heightIn(max = 200.dp)
//                            ) {
//                                items(if(game.game_players.isNullOrEmpty()) game.game_players_team else game.game_players) { gameName ->
//                                    Text(
//                                        text = gameName,
//                                        style = TextStyle.Default.copy(
//                                            fontSize = 16.sp,
//                                            color = Color.White
//                                        ),
//                                        modifier = Modifier.padding(
//                                            top = 2.dp,
//                                            bottom = 2.dp,
//                                            start = 10.dp,
//                                            end = 10.dp
//                                        )
//                                    )
//                                }
//                            }
//                            Spacer(modifier = Modifier.height(5.dp))
//                        }
//                    }
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
    Baseboard()

}
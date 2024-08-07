package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.ClickHandler
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel

/***** FUNÇÃO PRINCIPAL *****/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamsListScreen(
    uiState: TeamsListUiState,
    modifier: Modifier = Modifier,
    onNewTeamClick: () -> Unit = {},
    onTeamClick: (Team) -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: TeamsListViewModel
) {
    val clickHandler = remember { ClickHandler() }

    var lastClickedTeamIndex by remember { mutableStateOf(-1) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var teamToDelete by remember { mutableStateOf<Team?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadTeams()
    }

    Column (
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
            .clickable { lastClickedTeamIndex = -1 }
    ){

        /***** CABEÇALHO *****/
        Header(
            titleHeader = "EQUIPES",
            backgroundColor = AzulUno,
            icon = painterResource(id = R.drawable.ic_g_team)
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
                ButtonInfo(
                    icon = painterResource(id = R.drawable.ic_add),
                    description = "Add",
                    onClick = onNewTeamClick
                ), // Posição 3 botão
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )

        /***** CORPO DA ESTRUTURA *****/
        Box(modifier) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.945f)
            ) {
                itemsIndexed(uiState.teams) { index, team ->
                    val showActions = index == lastClickedTeamIndex
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (lastClickedTeamIndex == index) {
                                        lastClickedTeamIndex = -1
                                    } else {
                                        lastClickedTeamIndex = index
                                    }
                                },
                                onLongClick = {
                                    onTeamClick(team)
                                }
                            )
                    ) {
                        Column(
                            Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp),
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
                                        text = team.team_name,
                                        style = TextStyle.Default.copy(
                                            fontSize = 18.sp,
                                            color = Color.White
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
                                                    onTeamClick(team)
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
                                            teamToDelete = team
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
                                .heightIn(max = 200.dp) // Limitar a altura
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
                            Spacer(modifier = Modifier.height(3.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 3.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                                    .heightIn(max = 200.dp)
                            ) {
                                items(team.team_players) { playerName ->
                                    Text(
                                        text = playerName,
                                        style = TextStyle.Default.copy(
                                            fontSize = 16.sp,
                                            color = Color.White
                                        ),
                                        modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
                                    )
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
                            viewModel.deleteTeam(teamToDelete!!)
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
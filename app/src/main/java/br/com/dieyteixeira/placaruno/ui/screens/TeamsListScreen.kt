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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
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
        Spacer(modifier = Modifier.height(5.dp))

        /***** CORPO DA ESTRUTURA *****/
        Box(modifier) {
            LazyColumn(Modifier.fillMaxSize()) {
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
                            Modifier.padding(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        ),
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 14.dp)
                                    )
                                    if (showActions) {
                                        IconButton(onClick = { onTeamClick(team) }) {
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
    Baseboard()

}
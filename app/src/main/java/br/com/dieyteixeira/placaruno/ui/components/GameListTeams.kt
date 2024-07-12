package br.com.dieyteixeira.placaruno.ui.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun GameListTeams(
    uiStateTList: TeamsListUiState,
    modifier: Modifier = Modifier,
    playerTeamCount: Int,
    teamsCount: Int,
    gameViewModel: GameViewModel,
    onTeamsSelected: (List<String>) -> Unit = {}
) {
    // Variáveis
    val maxTeamSize = teamsCount
    val maxPlayerSize = playerTeamCount
    val firstColumnCount = (teamsCount + 1) / 2
    val secondColumnCount = teamsCount / 2

    val availableTeams = remember(uiStateTList) {
        uiStateTList.teams.map { it.team_name to it.team_players.size }
    }

    val membersTeams = remember(uiStateTList) {
        uiStateTList.teams.map { it.team_name to it.team_players }
    }

    val selectedTeams by gameViewModel.selectedTeams.collectAsState()

    var snackbarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            delay(2000)
            snackbarVisible = false
        }
    }

    fun isTeamInSelected(teamName: String): Boolean {
        return selectedTeams.contains(teamName)
    }

    fun addTeamToSelectedList(teamName: String) {
        val team = uiStateTList.teams.find { it.team_name == teamName }
        if (team != null && !isTeamInSelected(teamName)) {
            if (selectedTeams.size < maxTeamSize) {
                if (team.team_players.size == maxPlayerSize) {
                    gameViewModel.addTeamToSelectedList(teamName)
                    onTeamsSelected(selectedTeams)
                } else {
                    snackbarVisible = true
                }
            } else {
                snackbarVisible = true
            }
        }
    }

    fun removeTeamFromSelectedList(teamName: String) {
        gameViewModel.removeTeamFromSelectedList(teamName)
        onTeamsSelected(selectedTeams)
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
                text = "Equipes Selecionadas",
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Lista de equipes selecionadas em duas colunas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 15.dp,
                        topEnd = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(start = 5.dp, end = 5.dp, top = 12.dp)
        ) {
            // Coluna esquerda
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
            ){
                selectedTeams.take(firstColumnCount).forEach { teamName ->
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(
                                    topStart = 17.dp,
                                    bottomStart = 17.dp,
                                    topEnd = 17.dp,
                                    bottomEnd = 17.dp
                                )
                            )
                            .padding(start = 2.dp, end = 2.dp, top = 0.dp, bottom = 2.dp)
                    ) {
                        GameListItem(
                            text = teamName,
                            isSelected = false,
                            onClick = { removeTeamFromSelectedList(teamName) }
                        )
                        if (teamName == teamName) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            bottomStart = 15.dp,
                                            topEnd = 15.dp,
                                            bottomEnd = 15.dp
                                        )
                                    )
                                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                            ) {
                                // Filtra apenas os jogadores da equipe selecionada
                                items(membersTeams.filter { it.first == teamName }) { (_, players) ->
                                    Text(
                                        text = players.joinToString("\n"),
                                        color = Color.DarkGray,
                                        style = TextStyle.Default.copy(
                                            fontSize = 16.sp,
                                            fontStyle = FontStyle.Italic
                                        ),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Coluna direita
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
            ){
                selectedTeams.drop(firstColumnCount).take(secondColumnCount).forEach { teamName ->
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(
                                    topStart = 17.dp,
                                    bottomStart = 17.dp,
                                    topEnd = 17.dp,
                                    bottomEnd = 17.dp
                                )
                            )
                            .padding(start = 2.dp, end = 2.dp, top = 0.dp, bottom = 2.dp)
                    ) {
                        GameListItem(
                            text = teamName,
                            isSelected = false,
                            onClick = { removeTeamFromSelectedList(teamName) }
                        )
                        if (teamName == teamName) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            bottomStart = 15.dp,
                                            topEnd = 15.dp,
                                            bottomEnd = 15.dp
                                        )
                                    )
                                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                            ) {
                                // Filtra apenas os jogadores da equipe selecionada
                                items(membersTeams.filter { it.first == teamName }) { (_, players) ->
                                    Text(
                                        text = players.joinToString("\n"),
                                        color = Color.DarkGray,
                                        style = TextStyle.Default.copy(
                                            fontSize = 16.sp,
                                            fontStyle = FontStyle.Italic
                                        ),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
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
                    text = if (selectedTeams.size > maxTeamSize) {"Equipe completa!"} else {"Número de jogadores incorreto!"},
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

        // Título "Lista de Equipes"
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
                text = "Lista de Equipes",
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Lista de equipes disponíveis
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
            items(availableTeams) { (teamName, playersNumber) ->
                val isSelected = isTeamInSelected(teamName)
                GameListItem(
                    text = teamName,
                    playersNumber = playersNumber,
                    isSelected = isSelected,
                    onClick = {
                        if (isSelected) {
                            removeTeamFromSelectedList(teamName)
                        } else {
                            addTeamToSelectedList(teamName)
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
    playersNumber: Int = 0,
    isSelected: Boolean,
    onClick: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {

    val switchState by gameViewModel.switchState.collectAsState()

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
        if (playersNumber > 0) {
            Box (modifier = Modifier.padding(end = 15.dp)) {
                Text(
                    text = "n° jog.: $playersNumber",
                    style = TextStyle.Default.copy(
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}
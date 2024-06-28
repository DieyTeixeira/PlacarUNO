package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import kotlinx.coroutines.delay

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun TeamsEditScreen(
    uiState: TeamsEditUiState,
    uiStateList: TeamsListUiState,
    currentPlayers: List<Player>,
    modifier: Modifier = Modifier,
    onSaveTeamClick: (List<Player>) -> Unit,
    onBackClick: () -> Unit = {},
) {

    /***** VARIÁVEIS *****/
    val topAppBarTitle = uiState.topAppBarTitle
    val title = uiState.title
    val titleFontStyle = TextStyle.Default.copy(fontSize = 25.sp)
    val focusManager = LocalFocusManager.current
    val maxTeamSize = 5
    val maxPlayersPerColumn = 3
    val context = LocalContext.current

    val initialTeamPlayers = remember(uiState.title) {
        initialTeamPlayers(uiState, uiStateList)
    }

    var combinedPlayers by remember { mutableStateOf(initialTeamPlayers) }

    LaunchedEffect(uiState.title, uiStateList) {
        combinedPlayers = initialTeamPlayers(uiState, uiStateList)
    }

    var snackbarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            delay(2000)
            snackbarVisible = false
        }
    }

    fun updateCombinedPlayers(player: Player) {
        if (combinedPlayers.contains(player)) {
            combinedPlayers = combinedPlayers.filter { it != player }
        } else {
            if (combinedPlayers.size < maxTeamSize) {
                combinedPlayers = combinedPlayers + player
            } else {
                snackbarVisible = true
            }
        }
    }

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(
            titleHeader = topAppBarTitle,
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
                    icon = painterResource(id = R.drawable.ic_save),
                    description = "Save",
                    onClick = {
                        focusManager.clearFocus()
                        onSaveTeamClick(combinedPlayers)
                    }
                ), // Posição 3 botão
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )

        /***** CORPO DA ESTRUTURA *****/
        Column(
            Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        uiState.onTitleChange(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(57.dp)
                        .padding(horizontal = 14.dp),
                    textStyle = titleFontStyle.copy(
                        color = Color.White,
                        fontSize = 19.sp
                    ),
                    label = {
                        Text(
                            text = "Nome",
                            style = TextStyle.Default.copy(
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 15.sp
                            )
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            /***** LISTAS DE JOGADORES *****/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(200.dp)
//                    .padding(vertical = 8.dp)
            ) {

                /* LISTA DE JOGADORE NA EQUIPE */
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = AzulUno.copy(alpha = 0.5f))
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "--- Jogadores na Equipe ---",
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    // Coluna esquerda
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        PlayersList(
                            players = combinedPlayers.take(3), // Pegando os primeiros 3 jogadores
                            maxTeamSize = maxTeamSize
                        ) { player ->
                            updateCombinedPlayers(player)
                        }
                    }
                    // Coluna direita
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        PlayersList(
                            players = combinedPlayers.drop(3).take(3), // Pegando os jogadores restantes
                            maxTeamSize = maxTeamSize
                        ) { player ->
                            updateCombinedPlayers(player)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                ){
                    if (snackbarVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                        ) {
                            Text(
                                text = "Equipe completa!",
                                color = Color.Red,
                                style = TextStyle.Default.copy(
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Italic
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                /* LISTA DE JOGADORES DISPONÍVEIS */

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = AzulUno.copy(alpha = 0.5f))
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "--- Lista de Jogadores ---",
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        PlayersList(currentPlayers, maxTeamSize) { player ->
                            if (combinedPlayers.size < maxTeamSize || combinedPlayers.contains(player)) {
                                combinedPlayers = if (combinedPlayers.contains(player)) {
                                    combinedPlayers.filter { it != player }
                                } else {
                                    combinedPlayers + player
                                }
                            } else {
                                snackbarVisible = true
                            }
                        }
                    }
                }
            }
        }
    }

    /***** RODAPÉ *****/
    Baseboard()

}

@Composable
private fun PlayersList(
    players: List<Player>,
    maxTeamSize: Int,
    onPlayerClick: (Player) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(players) { player ->
            PlayerItem(
                player = player,
                isSelected = players.contains(player),
                onPlayerClick = {
                    onPlayerClick(player)
                }
            )
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    isSelected: Boolean,
    onPlayerClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clickable(onClick = onPlayerClick)
    ) {
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            tint = if (isSelected) Color.White else Color.Transparent,
            modifier = Modifier
                .size(30.dp)
                .padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = player.player_name ?: "",
            style = TextStyle.Default.copy(
                fontSize = 18.sp,
                color = if (isSelected) Color.Gray else Color.White
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

// Função para inicializar os jogadores da equipe
private fun initialTeamPlayers(uiState: TeamsEditUiState, uiStateList: TeamsListUiState): List<Player> {
    val initialTeam = uiStateList.teams.firstOrNull { it.team_name == uiState.title }
    return initialTeam?.team_players?.map { playerName ->
        Player(player_name = playerName)
    } ?: emptyList()
}
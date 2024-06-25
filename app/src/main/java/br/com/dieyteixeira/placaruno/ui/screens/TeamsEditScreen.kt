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
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import kotlinx.coroutines.delay

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun TeamsEditScreen(
    uiState: TeamsEditUiState,
    modifier: Modifier = Modifier,
    onSaveTeamClick: (List<Player>) -> Unit,
    onBackClick: () -> Unit = {},
) {

    /***** VARIÁVEIS *****/
    var isNameEmpty by remember { mutableStateOf(true) }
    val topAppBarTitle = uiState.topAppBarTitle
    val title = uiState.title
    val titleFontStyle = TextStyle.Default.copy(fontSize = 25.sp)
    val focusManager = LocalFocusManager.current
    val player_team = remember { mutableStateOf<List<Player>>(emptyList()) }
    val maxTeamSize = 5
    val maxPlayersPerColumn = 3
    val context = LocalContext.current
    var snackbarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            delay(2000)
            snackbarVisible = false
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
                if (isNameEmpty) {
                    null // Posição 3 sem botão
                } else {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_save),
                        description = "Save",
                        onClick = {
                            focusManager.clearFocus()
                            onSaveTeamClick(player_team.value)
                        }
                    ) // Posição 3 botão
                },
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
                        isNameEmpty = it.isEmpty()
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

            /***** JOGADORES SELECIONADOS *****/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
            ) {
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
                Row {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        player_team.value.take(maxPlayersPerColumn).forEachIndexed { index, player ->
                            item {
                                Text(
                                    text = player.player_name ?: "",
                                    color = Color.White,
                                    style = TextStyle.Default.copy(
                                        fontSize = 18.sp
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        player_team.value.drop(maxPlayersPerColumn).take(maxPlayersPerColumn).forEachIndexed { index, player ->
                            item {
                                Text(
                                    text = player.player_name ?: "",
                                    color = Color.White,
                                    style = TextStyle.Default.copy(
                                        fontSize = 18.sp
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (snackbarVisible) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(color = Color.White)
                    ){
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

            /***** LISTA DE JOGADORES *****/
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
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(uiState.players) { index, player ->
                    Column(
                        Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        PlayerItem(
                            player = player,
                            isSelected = player_team.value.contains(player),
                            isInTeam = player_team.value.contains(player),
                            onPlayerClick = {
                                val isPlayerInTeam = player_team.value.contains(player)

                                if (isPlayerInTeam) {
                                    player_team.value = player_team.value - player
                                } else {
                                    if (player_team.value.size < maxTeamSize) {
                                        player_team.value = player_team.value + player
                                    } else {
                                        snackbarVisible = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    /***** RODAPÉ *****/
    Baseboard()

}

@Composable
fun PlayerItem(
    player: Player,
    isSelected: Boolean,
    isInTeam: Boolean,
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
            tint = if (isInTeam) Color.White else Color.Transparent,
            modifier = Modifier
                .size(30.dp)
                .padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = player.player_name,
            style = TextStyle.Default.copy(
                fontSize = 18.sp,
                color = if (isSelected) Color.Gray else Color.White
            ),
            modifier = Modifier.weight(1f)
        )
    }
}


/***** VISUALIZAÇÃO ADICIONAR *****/
@Preview(showBackground = true)
@Composable
fun TeamsEditScreenPreview() {
    PlacarUNOTheme {
        TeamsEditScreen(
            uiState = TeamsEditUiState(
                topAppBarTitle = "CADASTRAR"
            ),
            onSaveTeamClick = {}
        )
    }
}

/***** VISUALIZAÇÃO EDITAR *****/
@Preview(showBackground = true)
@Composable
fun TeamsEditScreenWithEditModePreview() {
    PlacarUNOTheme {
        TeamsEditScreen(
            uiState = TeamsEditUiState(
                topAppBarTitle = "EDITAR",
                isDeleteEnabled = true
            ),
            onSaveTeamClick = {}
        )
    }
}
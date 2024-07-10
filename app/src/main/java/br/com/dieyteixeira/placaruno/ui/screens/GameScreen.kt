package br.com.dieyteixeira.placaruno.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GameListPlayers
import br.com.dieyteixeira.placaruno.ui.components.CountPlayerSelector
import br.com.dieyteixeira.placaruno.ui.components.CountPlayerTeamSelector
import br.com.dieyteixeira.placaruno.ui.components.GameListTeams
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.components.NewGameHeader
import br.com.dieyteixeira.placaruno.ui.components.SwitchButtonNG
import br.com.dieyteixeira.placaruno.ui.components.CountTeamSelector
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import com.google.android.exoplayer2.util.Log
import kotlinx.coroutines.launch

/***** FUNÇÃO PRINCIPAL *****/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewGameScreen(
    onBackClick: () -> Unit = {},
    uiStateTList: TeamsListUiState,
    uiStatePList: PlayersListUiState,
    onSaveClick: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {

    Log.d("NewGameScreen", "Entered NewGameScreen Composable")

    val scope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val switchState by gameViewModel.switchState.collectAsState()
    val playerCount by gameViewModel.playerCount.collectAsState()
    val teamCount by gameViewModel.teamCount.collectAsState()
    val playerTeamCount by gameViewModel.playerTeamCount.collectAsState()

    Log.d("NewGameScreen", "Switch state: $switchState")
    Log.d("NewGameScreen", "Player count: $playerCount")
    Log.d("NewGameScreen", "Team count: $teamCount")
    Log.d("NewGameScreen", "Player team count: $playerTeamCount")

    var playersTotalCount by remember {
        mutableStateOf(
            if (switchState) {
                playerTeamCount * teamCount
            } else {
                playerCount
            }
        )
    }

    LaunchedEffect(switchState, playerCount, teamCount, playerTeamCount) {
        playersTotalCount = if (switchState) {
            playerTeamCount * teamCount
        } else {
            playerCount
        }
        Log.d("NewGameScreen", "Updated playersTotalCount: $playersTotalCount")
    }


    Column (
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        Log.d("NewGameScreen", "Setting up UI components")

        /***** CABEÇALHO *****/
        Header(
            titleHeader = "NOVO JOGO",
            backgroundColor = VermelhoUno,
            icon = painterResource(id = R.drawable.ic_g_new_game)
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
                        onSaveClick()
                    }
                ), // Posição 3 botão
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(5.dp))

        // ABAS
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            VermelhoUno.copy(alpha = 0.65f),
                            VermelhoUno.copy(alpha = 0.9f),
                            VermelhoUno.copy(alpha = 0.9f),
                            VermelhoUno.copy(alpha = 0.65f)
                        )
                    )
                )
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_setup),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(30.dp)
                        .padding(1.dp)
                )
            }
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 }
            ) {
                Image(
                    painter = painterResource(id = if (switchState) { R.drawable.ic_g_team } else { R.drawable.ic_g_player }),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(1.dp)
                )
            }
            // Adicione mais abas conforme necessário
        }

        // CONTEÚDO DAS ABAS (EXEMPLO)
        when (selectedTabIndex) {
            0 -> {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Gray.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 15.dp,
                                topEnd = 0.dp,
                                bottomEnd = 15.dp
                            )
                        )
                        .padding(10.dp)
                ) {
                    Column {
                        NewGameHeader(titleHeader = "Equipes")
                        SwitchButtonNG(
                            checkedThumbColor = VermelhoUno, // Cor do thumb quando ativado
                            uncheckedThumbColor = Color.Gray, // Cor do thumb quando desativado
                            checkedTrackColor = Color.LightGray, // Cor do fundo quando ativado
                            uncheckedTrackColor = Color.LightGray, // Cor do fundo quando desativado
                            checkedBorderColor = VermelhoUno, // Remover contorno quando ativado
                            uncheckedBorderColor = Color.Gray, // Remover contorno quando desativado
                            switchState = switchState,
                            onSwitchChange = { state ->
                                gameViewModel.setSwitchState(state)
                            }
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        if (switchState) {
                            NewGameHeader(titleHeader = "Jogadores por Equipe")
                            Spacer(modifier = Modifier.height(5.dp))
                            CountPlayerTeamSelector(
                                playerTeamCount = playerTeamCount,
                                teamCount = teamCount,
                                onPlayerTeamCountChange = { count ->
                                    gameViewModel.setPlayerTeamCount(count)
                                }
                            )
                            NewGameHeader(titleHeader = "Número de Equipes")
                            Spacer(modifier = Modifier.height(5.dp))
                            CountTeamSelector(
                                teamCount = teamCount,
                                playerTeamCount = playerTeamCount,
                                onTeamCountChange = { count ->
                                    gameViewModel.setTeamCount(count)
                                }
                            )
                        } else {
                            NewGameHeader(titleHeader = "Número de Jogadores")
                            Spacer(modifier = Modifier.height(5.dp))
                            CountPlayerSelector(
                                playerCount = playerCount,
                                onPlayerCountChange = { count ->
                                    gameViewModel.setPlayerCount(count)
                                }
                            )
                        }
                    }
                    Log.d("NewGameScreen", "Displaying content for tab 0")
                }
            }
            1 -> {
                Spacer(modifier = Modifier.height(4.dp))
                if (switchState) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Transparent
                            )
                            .padding(10.dp)
                    ) {
                        GameListTeams(
                            uiStateTList = uiStateTList,
                            modifier = Modifier,
                            teamsCount = teamCount,
                            playerTeamCount = playerTeamCount,
                            gameViewModel = gameViewModel
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Transparent
                            )
                            .padding(10.dp)
                    ) {
                        GameListPlayers(
                            uiStatePList = uiStatePList,
                            modifier = Modifier,
                            playersCount = playerCount,
                            gameViewModel = gameViewModel
                        )
                    }
                }
                Log.d("NewGameScreen", "Displaying content for tab 1")
            }
        }
    }

    /***** RODAPÉ *****/
    Baseboard()

}

///***** PREVIEW *****/
//@Preview(showBackground = true)
//@Composable
//fun PreviewScoreTab() {
//    NewGameScreen().apply {
//        var selectedTabIndex = 0 // Simula a seleção da aba de pontuação
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewPlayersTab() {
//    NewGameScreen().apply {
//        var selectedTabIndex = 1 // Simula a seleção da aba de jogadores
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewTeamTab() {
//    NewGameScreen().apply {
//        var selectedTabIndex = 2 // Simula a seleção da aba de equipes
//    }
//}
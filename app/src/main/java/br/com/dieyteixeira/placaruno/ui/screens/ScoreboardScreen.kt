package br.com.dieyteixeira.placaruno.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

/***** FUNÇÃO PRINCIPAL *****/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScoreboardScreen(
    onBackClick: () -> Unit = {},
) {

    Column (
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

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
                null, // Posição 3 sem botão
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(5.dp))

//        Box (
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(160.dp)
//                .background(
//                    color = Color.Gray.copy(alpha = 0.4f),
//                    shape = RoundedCornerShape(
//                        topStart = 0.dp,
//                        bottomStart = 15.dp,
//                        topEnd = 0.dp,
//                        bottomEnd = 15.dp
//                    )
//                )
//                .padding(10.dp)
//        ){
//            ListPlayersGame(playersTotalCount = playersTotalCount, selectedPlayers = selectedPlayers)
//        }
//        Box (modifier = Modifier.fillMaxSize(0.95f)) {
//            PokerTable(playersTotalCount = playersTotalCount)
//            RotationGame()
//        }
    }

    /***** RODAPÉ *****/
    Baseboard()

}
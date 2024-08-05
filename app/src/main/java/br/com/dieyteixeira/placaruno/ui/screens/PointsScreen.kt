package br.com.dieyteixeira.placaruno.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.components.ListPlayersGame
import br.com.dieyteixeira.placaruno.ui.components.PokerTable
import br.com.dieyteixeira.placaruno.ui.components.Pontuation
import br.com.dieyteixeira.placaruno.ui.components.RotationGame
import br.com.dieyteixeira.placaruno.ui.states.PointsUiState
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerOrTeam
import br.com.dieyteixeira.placaruno.ui.viewmodels.PontuationViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth

/***** FUNÇÃO PRINCIPAL *****/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PointsScreen(
    onBackClick: () -> Unit = {},
    userEmail: String,
    gameId: String,
    name: String,
    score: Int,
    viewModel: PontuationViewModel = viewModel()
) {
    Log.d("Navigation", "PointsEditScreen composable called")

    val playerName = name
    val playerScore = score
    val pontuacaoTotal by remember { derivedStateOf { viewModel.pontuacaoTotal } }
    val newTotalScore = playerScore + pontuacaoTotal

    Column (
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(
            titleHeader = "PONTUAÇÃO",
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

        Spacer(modifier = Modifier.height(10.dp))

        Column (
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            Text(text = "Player: " + playerName, fontSize = 20.sp, color = Color.White)
            Row {
                Text(text = "Score: " + playerScore, fontSize = 20.sp, color = Color.White)
                Text(text = "+ " + pontuacaoTotal, fontSize = 24.sp, color = Color(0xFFFFC000))
            }
            Text(text = "Pontuação Total: " + (playerScore + pontuacaoTotal), fontSize = 24.sp, color = Color.White)
            Pontuation()
        }


        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                viewModel.updateScore(userEmail, gameId, playerName, newTotalScore,
                    onSuccess = {
                        onBackClick()
                    },
                    onFailure = { e ->
                        Log.e("Firebase", "Failed to update score", e)
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Salvar Pontuação", style = TextStyle(color = Color.White, fontSize = 20.sp))
        }

    }

    /***** RODAPÉ *****/
    Baseboard(color = Color.Transparent)

}
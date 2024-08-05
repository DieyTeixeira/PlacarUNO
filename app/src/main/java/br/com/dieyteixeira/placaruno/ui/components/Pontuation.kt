package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.toGame
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerOrTeam
import br.com.dieyteixeira.placaruno.ui.viewmodels.PontuationViewModel
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Carta(val cartasNome: String, val cartasValor: Int, val cartasImg: Int)

@Composable
fun Pontuation(
    viewModel: PontuationViewModel = viewModel()
) {
    val cartas = listOf(
        Carta("carta_1", 1, R.drawable.carta_1),
        Carta("carta_2", 2, R.drawable.carta_2),
        Carta("carta_3", 3, R.drawable.carta_3),
        Carta("carta_mais_2", 20, R.drawable.carta_mais_2),
        Carta("carta_inverter", 20, R.drawable.carta_inverter),
        Carta("carta_mais_4", 50, R.drawable.carta_mais_4),
        Carta("carta_muda_cor", 50, R.drawable.carta_muda_cor),
    )

    val cartasSelecionadas by remember { mutableStateOf(viewModel.cartasSelecionadas) }
    val pontuacaoTotal by remember { derivedStateOf { viewModel.pontuacaoTotal } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp)
    ){
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .background(Color.Gray)
//                .padding(16.dp)
//        ){
//            Text(
//                text = "Pontuação Total: $pontuacaoTotal",
//                fontSize = 20.sp,
//                color = Color.White,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp)
                .background(Color.Gray)
                .padding(10.dp),
        ){
            val rows = cartasSelecionadas.chunked(8)
            rows.forEach { row ->
                LazyRow {
                    items(row) { carta ->
                        Image(
                            painter = painterResource(id = carta.cartasImg),
                            contentDescription = carta.cartasNome,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(3.dp)
                                .clickable {
                                    viewModel.removerCarta(carta)
                                }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Gray)
                .padding(10.dp)
        ){
            val rows = cartas.chunked(8)
            rows.forEach { row ->
                LazyRow {
                    items(row) { carta ->
                        Image(
                            painter = painterResource(id = carta.cartasImg),
                            contentDescription = carta.cartasNome,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(3.dp)
                                .clickable {
                                    viewModel.adicionarCarta(carta)
                                }
                        )
                    }
                }
            }
        }
    }
}
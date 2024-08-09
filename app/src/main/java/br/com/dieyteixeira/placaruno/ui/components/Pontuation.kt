package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
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

data class Carta(val cartasNome: String, val cartasValor: Int, val cartasImg: Int, val cartaCor: Color? = null)

@Composable
fun Pontuation(
    viewModel: PontuationViewModel = viewModel()
) {
    val colorOptions = listOf(VerdeUno, AzulUno, VermelhoUno, AmareloUno)

    val cartas = listOf(
        Carta("carta_0", 0, R.drawable.carta_0, colorOptions.random()),
        Carta("carta_1", 1, R.drawable.carta_1, colorOptions.random()),
        Carta("carta_2", 2, R.drawable.carta_2, colorOptions.random()),
        Carta("carta_3", 3, R.drawable.carta_3, colorOptions.random()),
        Carta("carta_4", 4, R.drawable.carta_4, colorOptions.random()),
        Carta("carta_5", 5, R.drawable.carta_5, colorOptions.random()),
        Carta("carta_6", 6, R.drawable.carta_6, colorOptions.random()),
        Carta("carta_7", 7, R.drawable.carta_7, colorOptions.random()),
        Carta("carta_8", 8, R.drawable.carta_8, colorOptions.random()),
        Carta("carta_9", 9, R.drawable.carta_9, colorOptions.random()),
        Carta("carta_mais_2", 20, R.drawable.carta_mais_2, colorOptions.random()),
        Carta("carta_inverter", 20, R.drawable.carta_inverter, colorOptions.random()),
        Carta("carta_bloqueio", 20, R.drawable.carta_bloqueio, colorOptions.random()),
        Carta("carta_trocar_cor", 50, R.drawable.carta_trocar_cor),
        Carta("carta_mais_4", 50, R.drawable.carta_mais_4),
    )

    val cartasSelecionadas by remember { mutableStateOf(viewModel.cartasSelecionadas) }
    val pontuacaoTotal by remember { derivedStateOf { viewModel.pontuacaoTotal } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp)
    ){
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Gray.copy(alpha = 1f),
                            Color.Gray.copy(alpha = 0.75f),
                            Color.Gray.copy(alpha = 0.75f),
                            Color.Gray.copy(alpha = 1f)
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
                text = "Cartas",
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 180.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 15.dp,
                        topEnd = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val rows = cartas.chunked(8)
            items(rows) { row ->
                Spacer(modifier = Modifier.height(2.5.dp))
                LazyRow (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    items(row) { carta ->
                        Spacer(modifier = Modifier.width(2.5.dp))
                        Image(
                            painter = painterResource(id = carta.cartasImg),
                            contentDescription = carta.cartasNome,
                            contentScale = ContentScale.Crop,
                            colorFilter = carta.cartaCor?.let { tint(it) },
                            modifier = Modifier
                                .size(height = 60.dp, width = 38.3.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(5.dp))
                                .clickable {
                                    viewModel.adicionarCarta(carta.copy(cartaCor = if (carta.cartaCor != null) colorOptions.random() else null))
                                }
                        )
                        Spacer(modifier = Modifier.width(2.5.dp))
                    }
                }
                Spacer(modifier = Modifier.height(2.5.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Gray.copy(alpha = 1f),
                            Color.Gray.copy(alpha = 0.75f),
                            Color.Gray.copy(alpha = 0.75f),
                            Color.Gray.copy(alpha = 1f)
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
                text = "Cartas Selecionadas",
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 15.dp,
                        topEnd = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val rows = cartasSelecionadas.chunked(8)
            items(rows) { row ->
                Spacer(modifier = Modifier.height(2.5.dp))
                LazyRow (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    items(row) { carta ->
                        Spacer(modifier = Modifier.width(2.5.dp))
                        Image(
                            painter = painterResource(id = carta.cartasImg),
                            contentDescription = carta.cartasNome,
                            contentScale = ContentScale.Crop,
                            colorFilter = carta.cartaCor?.let { tint(it) },
                            modifier = Modifier
                                .size(height = 60.dp, width = 38.3.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(5.dp))
                                .clickable {
                                    viewModel.removerCarta(carta)
                                }
                        )
                        Spacer(modifier = Modifier.width(2.5.dp))
                    }
                }
                Spacer(modifier = Modifier.height(2.5.dp))
            }
        }
    }
}
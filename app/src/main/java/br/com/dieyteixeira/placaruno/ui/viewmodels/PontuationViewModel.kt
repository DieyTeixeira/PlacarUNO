package br.com.dieyteixeira.placaruno.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.toGame
import br.com.dieyteixeira.placaruno.ui.components.Carta
import br.com.dieyteixeira.placaruno.ui.states.PointsUiState
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardEditUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PontuationViewModel: ViewModel() {

    private val _cartasSelecionadas = mutableStateListOf<Carta>()
    val cartasSelecionadas: List<Carta> get() = _cartasSelecionadas

    fun adicionarCarta(carta: Carta) {
        _cartasSelecionadas.add(carta)
    }

    fun removerCarta(carta: Carta) {
        _cartasSelecionadas.remove(carta)
    }

    val pontuacaoTotal: Int
        get() = _cartasSelecionadas.sumOf { it.cartasValor }
}
package br.com.dieyteixeira.placaruno.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.toGame
import br.com.dieyteixeira.placaruno.ui.components.Carta
import br.com.dieyteixeira.placaruno.ui.states.GameUiState
import br.com.dieyteixeira.placaruno.ui.states.PointsUiState
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardEditUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PontuationViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    private val _updateStatus = MutableStateFlow<Result<Unit>?>(null)
    val updateStatus: StateFlow<Result<Unit>?> = _updateStatus

    fun updatePlayerScore(gameID: String, playerName: String, newTotalScore: Int) {
        viewModelScope.launch {
            try {
                // Atualiza o campo específico de pontuação dentro do documento do jogo
                userEmail?.let {
                    db.collection(it) // Referencia a coleção do usuário
                        .document("jogos") // Documento "jogos"
                        .collection("lista") // Subcoleção "lista"
                        .document(gameID) // Documento do jogo
                        .update(mapOf("game_scores.$playerName" to newTotalScore)) // Atualiza a pontuação do jogador
                        .await()
                }

                _updateStatus.value = Result.success(Unit)
            } catch (e: Exception) {
                // Trata possíveis exceções
                _updateStatus.value = Result.failure(e)
                e.printStackTrace()
            }
        }
    }

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
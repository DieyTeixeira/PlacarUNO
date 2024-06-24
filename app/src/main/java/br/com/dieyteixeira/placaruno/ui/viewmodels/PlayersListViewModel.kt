package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlayersListViewModel(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlayersListUiState> =
        MutableStateFlow(PlayersListUiState())
    val uiState get() = _uiState
    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    init {
        viewModelScope.launch {
            loadPlayers()
        }
    }

    suspend fun loadPlayers() {
        userEmail?.let { email ->
            try {
                repository.loadPlayers(email).collect { players ->
                    _uiState.value = _uiState.value.copy(players = players)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePlayer(player: Player) {
        userEmail?.let { email ->
            viewModelScope.launch {
                try {
                    repository.deleteP(email, player)
                    // Atualizar a lista de jogadores no UI State após a exclusão
                    _uiState.value = _uiState.value.copy(
                        players = _uiState.value.players.filterNot { it.player_id == player.player_id }
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardsListUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ScoreboardsListViewModel(
    private val repository: GamesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ScoreboardsListUiState> =
        MutableStateFlow(ScoreboardsListUiState())
    val uiState get() = _uiState
    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    init {
        viewModelScope.launch {
            loadGames()
        }
    }

    suspend fun loadGames() {
        userEmail?.let { email ->
            try {
                repository.loadGames(email).collect { games ->
                    _uiState.value = _uiState.value.copy(games = games)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteGame(game: Game) {
        userEmail?.let { email ->
            viewModelScope.launch {
                try {
                    repository.deleteP(email, game)
                    _uiState.value = _uiState.value.copy(
                        games = _uiState.value.games.filterNot { it.game_id == game.game_id }
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
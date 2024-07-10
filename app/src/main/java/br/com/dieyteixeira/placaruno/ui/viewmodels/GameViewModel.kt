package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.toGame
import br.com.dieyteixeira.placaruno.ui.states.GameUiState
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class GameViewModel(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: GamesRepository
) : ViewModel() {

    /***** CONFIGURAÇÕES *****/
    private val _switchState = MutableStateFlow(false)
    val switchState: StateFlow<Boolean> = _switchState
    fun setSwitchState(state: Boolean) { _switchState.value = state }


    private val _playerCount = MutableStateFlow(2)
    val playerCount: StateFlow<Int> = _playerCount
    fun setPlayerCount(count: Int) { _playerCount.value = count }

    private val _teamCount = MutableStateFlow(2)
    val teamCount: StateFlow<Int> = _teamCount
    fun setTeamCount(count: Int) { _teamCount.value = count }

    private val _playerTeamCount = MutableStateFlow(2)
    val playerTeamCount: StateFlow<Int> = _playerTeamCount
    fun setPlayerTeamCount(count: Int) { _playerTeamCount.value = count }

    /***** SELEÇÃO DE JOGADORES *****/
    private val _selectedPlayers = MutableStateFlow<List<String>>(emptyList())
    val selectedPlayers: StateFlow<List<String>> = _selectedPlayers.asStateFlow()
    fun addPlayerToSelectedList(playerName: String) { _selectedPlayers.value = _selectedPlayers.value + playerName }
    fun removePlayerFromSelectedList(playerName: String) { _selectedPlayers.value = _selectedPlayers.value - playerName }

    /***** SELEÇÃO DE EQUIPES *****/
    private val _selectedTeams = MutableStateFlow<List<String>>(emptyList())
    val selectedTeams: StateFlow<List<String>> = _selectedTeams.asStateFlow()
    fun addTeamToSelectedList(teamName: String) { _selectedTeams.value = _selectedTeams.value + teamName }
    fun removeTeamFromSelectedList(teamName: String) { _selectedTeams.value = _selectedTeams.value - teamName }

    private val _uiState: MutableStateFlow<GameUiState> =
        MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["gameId"]

    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email.also {
            Log.d("GameViewModel", "User email: $it")
        }

//    init {
//        _uiState.update { currentState ->
//            currentState.copy(
//                onTitleChange = { title ->
//                    _uiState.update {
//                        it.copy(title = title)
//                    }
//                },
//                topAppBarTitle = "ADICIONAR"
//            )
//        }
//        id?.let {
//            loadGameData(it)
//        }
//    }

//    private fun loadGameData(id: String) {
//        userEmail?.let { email ->
//            viewModelScope.launch {
//                gamesRepository.findById(email, id)
//                    .filterNotNull()
//                    .mapNotNull { it.toGame() }
//                    .collectLatest { game ->
//                        _uiState.update { currentState ->
//                            currentState.copy(
//                                topAppBarTitle = "EDITAR",
//                                title = game.game_name,
//                                teams = game.game_teams,
//                                players = game.game_players,
//                                scores = game.game_scores,
//                                isDeleteEnabled = true
//                            )
//                        }
//                    }
//            }
//        }
//    }

    suspend fun save() {
        userEmail?.let { email ->
            with(_uiState.value) {
                val game = Game(
                    game_id = id ?: UUID.randomUUID().toString(),
                    game_name = title,
                    game_teams = if (switchState.value) {
                        selectedTeams.value
                    } else {
                        emptyList()
                    },
                    game_players = if (switchState.value) {
                        emptyList()
                    } else {
                        selectedPlayers.value
                    }
                )
                gamesRepository.save(email, game)
            }
        } ?: run {
            Log.e("GameViewModel", "User email is null, cannot save game.")
        }
    }


    suspend fun delete() {
        userEmail?.let { email ->
            id?.let {
                gamesRepository.delete(email, id)
            }
        } ?: run {
            Log.e("GameViewModel", "User email is null, cannot delete game.")
        }
    }
}
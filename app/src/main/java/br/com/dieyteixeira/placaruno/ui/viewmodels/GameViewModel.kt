package br.com.dieyteixeira.placaruno.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.ui.states.GameUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class GameViewModel(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: GamesRepository,
    private val repository: TeamsRepository
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

    init {
        viewModelScope.launch {
            loadTeams()
        }
    }

    suspend fun loadTeams() {
        userEmail?.let { email ->
            try {
                repository.loadTeams(email).collect { teams ->
                    _uiState.value = _uiState.value.copy(teams = teams)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun save() {
        userEmail?.let { email ->
            with(_uiState.value) {
                val currentDateTime = LocalDateTime.now()
                val formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))

                val gameNameWithDateTime = "Data: $formattedDate - Hora: $formattedTime"

                Log.d("GameViewModel", "Selected teams: $selectedTeams")

                val teamPlayersMap = if (switchState.value) {
                    Log.d("GameViewModel", "Teams in uiState: ${uiState.value.teams}")
                    selectedTeams.value.associateWith { teamName ->
                        val team = uiState.value.teams.find { it.team_name == teamName }
                        val players = team?.team_players ?: emptyList()
                        Log.d("GameViewModel", "Players for team $teamName: $players")
                        players
                    }
                } else {
                    emptyMap()
                }

                Log.d("GameViewModel", "Team Players Map: $teamPlayersMap")

                val game = Game(
                    game_id = id ?: UUID.randomUUID().toString(),
                    game_name = gameNameWithDateTime,
                    game_teams = if (switchState.value) selectedTeams.value else emptyList(),
                    game_players = if (switchState.value) emptyList() else selectedPlayers.value,
                    game_players_team = if (switchState.value) teamPlayersMap else emptyMap()
                )

                Log.d("GameViewModel", "Game to be saved: $game")
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
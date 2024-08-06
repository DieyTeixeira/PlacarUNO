package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Game
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.repositories.toGame
import br.com.dieyteixeira.placaruno.repositories.toTeam
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardEditUiState
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

// Defina uma enumeração para o tipo de dados
enum class PlayerOrTeam {
    PLAYERS, TEAMS
}

class ScoreboardEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: GamesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ScoreboardEditUiState> =
        MutableStateFlow(ScoreboardEditUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["gameId"]

    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTitleChange = { title ->
                    _uiState.update {
                        it.copy(title = title)
                    }
                },
                topAppBarTitle = "ADICIONAR"
            )
        }
        id?.let {
            loadGameData(it)
        }
    }

    private val _verificatePlayers = MutableStateFlow<PlayerOrTeam>(PlayerOrTeam.PLAYERS)
    val verificatePlayers: StateFlow<PlayerOrTeam> = _verificatePlayers.asStateFlow()

    private val _selectedPlayersOrTeams = MutableStateFlow<List<String>>(emptyList())
    val selectedPlayersOrTeams: StateFlow<List<String>> = _selectedPlayersOrTeams.asStateFlow()

    private val _selectedPlayers = MutableStateFlow<List<String>>(emptyList())
    val selectedPlayers: StateFlow<List<String>> = _selectedPlayers.asStateFlow()

    private val _playerCount = MutableStateFlow(0)
    val playerCount: StateFlow<Int> = _playerCount.asStateFlow()

    private val _teamPlayerCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val teamPlayerCounts: StateFlow<Map<String, Int>> = _teamPlayerCounts.asStateFlow()

    private val _gamePoints = MutableStateFlow<Map<String, Int>>(emptyMap())
    val gamePoints: StateFlow<Map<String, Int>> = _gamePoints.asStateFlow()

    private fun loadGameData(id: String) {
        userEmail?.let { email ->
            viewModelScope.launch {
                gamesRepository.findById(email, id)
                    .filterNotNull()
                    .mapNotNull { it.toGame() }
                    .collectLatest { game ->

                        Log.d("ScoreboardEditViewModel", "Loaded Game: ${game.game_name}")
                        Log.d("ScoreboardEditViewModel", "Players: ${game.game_players}")
                        Log.d("ScoreboardEditViewModel", "Teams: ${game.game_teams}")

                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "EDITAR",
                                title = game.game_name,
                                gameId = game.game_id,
                                isDeleteEnabled = true
                            )
                        }

                        // Atualizar o tipo de dados (jogadores ou equipes)
                        _verificatePlayers.update {
                            if (game.game_players.isNotEmpty()) {
                                PlayerOrTeam.PLAYERS
                            } else {
                                PlayerOrTeam.TEAMS
                            }
                        }

                        // Atualizar os jogadores selecionados
                        _selectedPlayersOrTeams.update {
                            when (_verificatePlayers.value) {
                                PlayerOrTeam.PLAYERS -> game.game_players
                                PlayerOrTeam.TEAMS -> game.game_teams
                            }
                        }

                        // Atualizar os jogadores selecionados
                        _selectedPlayers.update {
                            when (_verificatePlayers.value) {
                                PlayerOrTeam.PLAYERS -> game.game_players
                                PlayerOrTeam.TEAMS -> {
                                    // Organize os jogadores das equipes em uma lista de listas
                                    val playersByTeam = game.game_players_team.values.toList()

                                    // Determinar a quantidade máxima de jogadores em qualquer equipe
                                    val maxPlayers = playersByTeam.maxOfOrNull { it.size } ?: 0

                                    // Intercalar jogadores das equipes
                                    val intercalatedPlayers = mutableListOf<String>()

                                    for (i in 0 until maxPlayers) {
                                        for (teamPlayers in playersByTeam) {
                                            if (i < teamPlayers.size) {
                                                intercalatedPlayers.add(teamPlayers[i])
                                            }
                                        }
                                    }

                                    intercalatedPlayers
                                }
                            }
                        }

                        // Atualizar a contagem de jogadores
                        _playerCount.update {
                            game.game_players.size.takeIf { it > 0 } ?: game.game_teams.size
                        }

                        // Atualizar a contagem de jogadores em cada equipe
                        _teamPlayerCounts.update {
                            game.game_teams.associateWith { teamName ->
                                game.game_players_team[teamName]?.size ?: 0
                            }
                        }

                        _gamePoints.update {
                            game.game_scores
                        }
                    }
            }
        }
    }

    suspend fun delete() {
        userEmail?.let { email ->
            id?.let {
                gamesRepository.delete(email, id)
            }
        }
    }
}
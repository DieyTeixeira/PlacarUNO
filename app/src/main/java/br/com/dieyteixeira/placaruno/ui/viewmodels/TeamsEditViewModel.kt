package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.repositories.toTeam
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class TeamsEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamsRepository: TeamsRepository,
    private val playersRepository: PlayersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TeamsEditUiState> =
        MutableStateFlow(TeamsEditUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["teamId"]

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
            loadTeamData(it)
        }
        viewModelScope.launch {
            loadPlayers()
        }
    }

    private fun loadTeamData(id: String) {
        userEmail?.let { email ->
            viewModelScope.launch {
                teamsRepository.findById(email, id)
                    .filterNotNull()
                    .mapNotNull { it.toTeam() }
                    .collectLatest { team ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "EDITAR",
                                title = team.team_name,
                                playerNames = team.team_players,
                                isDeleteEnabled = true
                            )
                        }
                    }
            }
        }
    }

    private suspend fun loadPlayers() {
        userEmail?.let { email ->
            try {
                playersRepository.loadPlayers(email).collect { players ->
                    _uiState.value = _uiState.value.copy(players = players)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun save(selectedPlayers: List<Player>) {
        userEmail?.let { email ->
            with(_uiState.value) {
                val playerNames = selectedPlayers.map { it.player_name }
                teamsRepository.save(
                    email,
                    Team(
                        team_id = id ?: UUID.randomUUID().toString(),
                        team_name = title,
                        team_players = playerNames
                    )
                )
            }
        }
    }

    suspend fun delete() {
        userEmail?.let { email ->
            id?.let {
                teamsRepository.delete(email, id)
            }
        }
    }
}
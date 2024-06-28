package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TeamsListViewModel(
    private val repository: TeamsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TeamsListUiState> =
        MutableStateFlow(TeamsListUiState())
    val uiState get() = _uiState
    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

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

    suspend fun loadPlayersForTeam() {
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



    fun deleteTeam(team: Team) {
        userEmail?.let { email ->
            viewModelScope.launch {
                try {
                    repository.deleteP(email, team)
                    _uiState.value = _uiState.value.copy(
                        teams = _uiState.value.teams.filterNot { it.team_id == team.team_id }
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.repositories.toTeam
import br.com.dieyteixeira.placaruno.ui.states.TeamsListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamsListViewModel(
    private val repository: TeamsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TeamsListUiState> =
        MutableStateFlow(TeamsListUiState())
    val uiState
        get() = _uiState
            .combine(repository.teams) { uiState, teams ->
                uiState.copy(teams = teams.map { it.toTeam() })
            }

    fun deleteTeam(team: Team) {
        viewModelScope.launch {
            repository.deleteP(team)
        }
    }

}
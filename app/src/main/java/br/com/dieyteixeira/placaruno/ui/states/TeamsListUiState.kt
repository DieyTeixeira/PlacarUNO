package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Team

data class TeamsListUiState (
    val teams: List<Team> = emptyList(),

    val uiStateList: List<Team> = emptyList()
)
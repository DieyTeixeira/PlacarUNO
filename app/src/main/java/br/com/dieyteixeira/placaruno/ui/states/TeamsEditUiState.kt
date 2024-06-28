package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team

data class TeamsEditUiState(
    val teamId: String? = null,
    val title: String = "",
    val playerNames: List<String> = emptyList(),
    val topAppBarTitle: String = "",
    val onTitleChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false,

    val teams: List<Team> = emptyList(),
    val players: List<Player> = emptyList(),
)
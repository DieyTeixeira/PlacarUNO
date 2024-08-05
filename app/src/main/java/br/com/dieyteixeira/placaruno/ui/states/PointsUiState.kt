package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team

data class PointsUiState(
    val gameId: String? = null,
    val playerName: String = "",
    val playerScore: Int = 0,

    val title: String = "",
    val playerNames: List<String> = emptyList(),
    val topAppBarTitle: String = "",
    val onTitleChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false,

    val teams: List<Team> = emptyList(),
    val players: List<Player> = emptyList(),
)
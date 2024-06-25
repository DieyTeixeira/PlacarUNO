package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Player

data class TeamsEditUiState(
    val teamId: String? = null,
    val title: String = "",
    val topAppBarTitle: String = "",
    val onTitleChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false,

    val players: List<Player> = emptyList(),
)
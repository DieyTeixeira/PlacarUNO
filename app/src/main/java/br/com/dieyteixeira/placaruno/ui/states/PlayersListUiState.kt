package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Player

data class PlayersListUiState(
    val players: List<Player> = emptyList(),
)
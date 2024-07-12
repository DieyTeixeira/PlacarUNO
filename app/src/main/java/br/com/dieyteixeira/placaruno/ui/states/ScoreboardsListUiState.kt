package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Game

data class ScoreboardsListUiState (
    val games: List<Game> = emptyList(),
)
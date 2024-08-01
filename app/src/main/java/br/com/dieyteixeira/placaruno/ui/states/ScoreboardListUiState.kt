package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.Game

data class ScoreboardListUiState (
    val games: List<Game> = emptyList(),
)
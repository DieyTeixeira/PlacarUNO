package br.com.dieyteixeira.placaruno.models

data class Score(
    val game_id: String = "",
    val game_name: String = "",
    val game_teams: List<String> = emptyList(),
    val game_players: List<String> = emptyList(),
    val game_scores: Map<String, Int> = emptyMap(),
    val game_players_team: Map<String, List<String>> = emptyMap(),
)
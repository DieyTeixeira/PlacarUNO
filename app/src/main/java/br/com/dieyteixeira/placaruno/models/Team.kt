package br.com.dieyteixeira.placaruno.models

data class Team(
    val team_id: String = "",
    val team_name: String = "",
    val team_players: List<String> = emptyList(),
)
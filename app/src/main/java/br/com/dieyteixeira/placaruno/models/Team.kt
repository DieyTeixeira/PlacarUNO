package br.com.dieyteixeira.placaruno.models

data class Team(
    val team_id: String = "",
    val team_name: String = "",
    val team_player1: String? = null,
    val team_player2: String? = null,
    val team_player3: String? = null,
    val team_player4: String? = null,
    val team_player5: String? = null,
    val team_player6: String? = null
)
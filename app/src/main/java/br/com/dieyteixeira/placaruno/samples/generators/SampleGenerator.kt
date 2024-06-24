package br.com.dieyteixeira.placaruno.samples.generators

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.models.Team
import kotlin.random.Random

fun generateRandomPlayers(
    amountPlayers: Int = 1
) = List(amountPlayers) {
    val index = it + 1
    Player(
        player_name = generateLoremIpsum(index),
    )
}

fun generateRandomTeams(
    amountTeams: Int = 1
) = List(amountTeams) {
    val index = it + 1
    Team(
        team_name = generateLoremIpsum(index),
    )
}

fun generateLoremIpsum(
    amountWords: Int = 1
) = LoremIpsum(
    Random.nextInt(1, if (amountWords > 1) amountWords else 2)
).values.first()
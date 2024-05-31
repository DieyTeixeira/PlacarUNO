package br.com.dieyteixeira.placaruno.samples.generators

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.dieyteixeira.placaruno.models.Player
import kotlin.random.Random

fun generateRandomPlayers(
    amountPlayers: Int = 1
) = List(amountPlayers) {
    val index = it + 1
    Player(
        title = generateLoremIpsum(index),
        isDone = index.mod(2) == 0
    )
}

fun generateLoremIpsum(
    amountWords: Int = 1
) = LoremIpsum(
    Random.nextInt(1, if (amountWords > 1) amountWords else 2)
).values.first()
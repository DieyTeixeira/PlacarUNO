package br.com.dieyteixeira.placaruno.models

import java.util.UUID

data class Player(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isDone: Boolean = false
)
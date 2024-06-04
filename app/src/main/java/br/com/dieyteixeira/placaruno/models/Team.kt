package br.com.dieyteixeira.placaruno.models

import java.util.UUID

data class Team (
    val idT: String = UUID.randomUUID().toString(),
    val titleT: String,
    val isDoneT: Boolean = false
)
package br.com.dieyteixeira.placaruno.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class TeamEntity(
    @PrimaryKey
    val idT: String = UUID.randomUUID().toString(),
    val titleT: String,
    val isDoneT: Boolean = false
)
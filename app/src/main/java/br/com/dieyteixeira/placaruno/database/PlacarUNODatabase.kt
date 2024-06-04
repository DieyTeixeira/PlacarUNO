package br.com.dieyteixeira.placaruno.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.dieyteixeira.placaruno.database.dao.PlayerDao
import br.com.dieyteixeira.placaruno.database.dao.TeamDao
import br.com.dieyteixeira.placaruno.database.entities.PlayerEntity
import br.com.dieyteixeira.placaruno.database.entities.TeamEntity

@Database(entities = [PlayerEntity::class, TeamEntity::class], version = 2)
abstract class PlacarUNODatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
}
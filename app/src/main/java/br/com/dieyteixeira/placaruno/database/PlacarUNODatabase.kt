package br.com.dieyteixeira.placaruno.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.dieyteixeira.placaruno.database.dao.PlayerDao
import br.com.dieyteixeira.placaruno.database.entities.PlayerEntity

@Database(entities = [PlayerEntity::class], version = 1)
abstract class PlacarUNODatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

}
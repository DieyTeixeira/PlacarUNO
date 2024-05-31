package br.com.dieyteixeira.placaruno.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dieyteixeira.placaruno.database.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM PlayerEntity")
    fun findAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM PlayerEntity WHERE id = :id")
    fun findById(id: String): Flow<PlayerEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(player: PlayerEntity)

    @Delete
    suspend fun delete(player: PlayerEntity)

}
package br.com.dieyteixeira.placaruno.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dieyteixeira.placaruno.database.entities.PlayerEntity
import br.com.dieyteixeira.placaruno.database.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Query("SELECT * FROM TeamEntity")
    fun findAll(): Flow<List<TeamEntity>>

    @Query("SELECT * FROM TeamEntity WHERE idT = :idT")
    fun findById(idT: String): Flow<TeamEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(team: TeamEntity)

    @Delete
    suspend fun delete(team: TeamEntity)

}
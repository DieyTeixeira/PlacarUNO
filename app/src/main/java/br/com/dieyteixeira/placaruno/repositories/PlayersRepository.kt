package br.com.dieyteixeira.placaruno.repositories

import br.com.dieyteixeira.placaruno.database.dao.PlayerDao
import br.com.dieyteixeira.placaruno.database.entities.PlayerEntity
import br.com.dieyteixeira.placaruno.models.Player
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PlayersRepository(
    private val dao: PlayerDao
) {

    val players get() = dao.findAll()

    suspend fun save(player: Player) = withContext(IO) {
        dao.save(player.toPlayerEntity())
    }

    suspend fun delete(id: String) {
        dao.delete(
            PlayerEntity(id = id, title = "")
        )
    }

    suspend fun deleteP(player: Player) = withContext(IO) {
        dao.delete(player.toPlayerEntity())
    }

    fun findById(id: String) = dao.findById(id)

}

fun Player.toPlayerEntity() = PlayerEntity(
    id = id,
    title = title,
    isDone = isDone
)

fun PlayerEntity.toPlayer() = Player(
    id = this.id,
    title = this.title,
    isDone = this.isDone
)
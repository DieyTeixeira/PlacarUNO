package br.com.dieyteixeira.placaruno.repositories

import br.com.dieyteixeira.placaruno.models.Player
import com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG
import com.google.android.exoplayer2.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayersRepository {

    private val db = FirebaseFirestore.getInstance()

    private fun playersCollection(userEmail: String) =
        db.collection(userEmail).document("jogadores").collection("lista")

    fun loadPlayers(userEmail: String): Flow<List<Player>> = flow {
        try {
            val snapshot = playersCollection(userEmail)
                .orderBy("player_name").get().await()
            val playersList = snapshot.documents.mapNotNull { it.toObject(Player::class.java) }
            emit(playersList)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading players", e)
            emit(emptyList())
        }
    }

    suspend fun save(userEmail: String, player: Player) = withContext(Dispatchers.IO) {
        try {
            val playerDoc = if (player.player_id.isBlank()) {
                playersCollection(userEmail).document()
            } else {
                playersCollection(userEmail).document(player.player_id)
            }
            playerDoc.set(player).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun delete(userEmail: String, id: String) = withContext(Dispatchers.IO) {
        try {
            playersCollection(userEmail).document(id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteP(userEmail: String, player: Player) = withContext(Dispatchers.IO) {
        try {
            playersCollection(userEmail).document(player.player_id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun findById(userEmail: String, id: String): Flow<Player?> = flow {
        try {
            val snapshot = playersCollection(userEmail).document(id).get().await()
            emit(snapshot.toObject(Player::class.java))
        } catch (e: Exception) {
            emit(null)
        }
    }
}

fun Player.toPlayer() = Player(
    player_id = this.player_id,
    player_name = this.player_name
)
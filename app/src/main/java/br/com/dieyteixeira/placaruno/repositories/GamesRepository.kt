package br.com.dieyteixeira.placaruno.repositories

import br.com.dieyteixeira.placaruno.models.Game
import com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG
import com.google.android.exoplayer2.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GamesRepository {

    private val db = FirebaseFirestore.getInstance()

    private fun gamesCollection(userEmail: String) =
        db.collection(userEmail).document("jogos").collection("lista")

    fun loadGames(userEmail: String): Flow<List<Game>> = flow {
        try {
            val snapshot = gamesCollection(userEmail)
                .orderBy("game_name").get().await()
            val gamesList = snapshot.documents.mapNotNull { it.toObject(Game::class.java) }
            emit(gamesList)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading game", e)
            emit(emptyList())
        }
    }

    suspend fun save(userEmail: String, game: Game) = withContext(Dispatchers.IO) {
        try {
            val gameDoc = if (game.game_id.isBlank()) {
                gamesCollection(userEmail).document()
            } else {
                gamesCollection(userEmail).document(game.game_id)
            }
            gameDoc.set(game).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun delete(userEmail: String, id: String) = withContext(Dispatchers.IO) {
        try {
            gamesCollection(userEmail).document(id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteP(userEmail: String, game: Game) = withContext(Dispatchers.IO) {
        try {
            gamesCollection(userEmail).document(game.game_id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun findById(userEmail: String, id: String): Flow<Game?> = flow {
        try {
            val snapshot = gamesCollection(userEmail).document(id).get().await()
            emit(snapshot.toObject(Game::class.java))
        } catch (e: Exception) {
            emit(null)
        }
    }

    suspend fun updateScore(userEmail: String, gameId: String, playerName: String, newScore: Int) = withContext(Dispatchers.IO) {
        try {
            // Referência ao documento do jogo
            val gameRef = db.collection(userEmail)
                .document("jogos")
                .collection("lista")
                .document(gameId)

            // Obter o documento do jogo
            val document = gameRef.get().await()
            if (document.exists()) {
                // Obter o campo game_scores como um Map e convertê-lo em um MutableMap
                val gameScores = document.get("game_scores") as? MutableMap<String, Long> ?: mutableMapOf()

                // Atualizar a pontuação do jogador
                val currentScore = gameScores[playerName] ?: 0
                gameScores[playerName] = currentScore + newScore

                // Atualizar o documento no Firestore
                gameRef.update("game_scores", gameScores).await()
            } else {
                throw Exception("Documento não encontrado")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}

fun Game.toGame() = Game(
    game_id = this.game_id,
    game_name = this.game_name,
    game_teams = this.game_teams,
    game_players = this.game_players,
    game_scores = this.game_scores,
    game_players_team = this.game_players_team,
)
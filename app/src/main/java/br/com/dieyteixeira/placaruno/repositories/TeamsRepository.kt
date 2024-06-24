package br.com.dieyteixeira.placaruno.repositories

import br.com.dieyteixeira.placaruno.models.Team
import com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG
import com.google.android.exoplayer2.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TeamsRepository {

    private val db = FirebaseFirestore.getInstance()

    private fun teamsCollection(userEmail: String) =
        db.collection(userEmail).document("equipes").collection("lista")

    fun loadTeams(userEmail: String): Flow<List<Team>> = flow {
        try {
            val snapshot = teamsCollection(userEmail)
                .orderBy("team_name").get().await()
            val teamsList = snapshot.documents.mapNotNull { it.toObject(Team::class.java) }
            emit(teamsList)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading teams", e)
            emit(emptyList())
        }
    }

    suspend fun save(userEmail: String, team: Team) = withContext(Dispatchers.IO) {
        try {
            val teamDoc = if (team.team_id.isBlank()) {
                teamsCollection(userEmail).document()
            } else {
                teamsCollection(userEmail).document(team.team_id)
            }
            teamDoc.set(team).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun delete(userEmail: String, id: String) = withContext(Dispatchers.IO) {
        try {
            teamsCollection(userEmail).document(id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteP(userEmail: String, team: Team) = withContext(Dispatchers.IO) {
        try {
            teamsCollection(userEmail).document(team.team_id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun findById(userEmail: String, id: String): Flow<Team?> = flow {
        try {
            val snapshot = teamsCollection(userEmail).document(id).get().await()
            emit(snapshot.toObject(Team::class.java))
        } catch (e: Exception) {
            emit(null)
        }
    }
}

fun Team.toTeam() = Team(
    team_id = this.team_id,
    team_name = this.team_name
)
package br.com.dieyteixeira.placaruno.repositories

import br.com.dieyteixeira.placaruno.models.DataUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import android.util.Log
import br.com.dieyteixeira.placaruno.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository {

    private val db = FirebaseFirestore.getInstance()

    private fun usersCollection(userEmail: String) =
        db.collection(userEmail).document("usuário").collection("lista")

    private fun usersInitCollection(email: String) =
        db.collection(email).document("usuário").collection("lista")

    fun loadUsers(userEmail: String): Flow<List<DataUser>> = flow {
        try {
            val snapshot = usersCollection(userEmail)
                .orderBy("user_email").get().await()
            val usersList = snapshot.documents.mapNotNull { it.toObject(DataUser::class.java) }
            emit(usersList)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun loadAllUsers(): Flow<List<DataUser>> = flow {
        try {
            val allUsersList = mutableListOf<DataUser>()

            val collectionsSnapshot = db.collectionGroup("lista").get().await()

            val usersList = collectionsSnapshot.documents.mapNotNull { it.toObject(DataUser::class.java) }
            allUsersList.addAll(usersList)

            emit(allUsersList)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun save(email: String, user: DataUser) = withContext(Dispatchers.IO) {
        try {
            val userDoc = usersInitCollection(email).document("usuário")
            userDoc.set(user).await()
        } catch (e: Exception) {
        }
    }

    suspend fun userDocumentExists(email: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val userDoc = usersInitCollection(email).document("usuário").get().await()
            Log.d("UsersRepository", "Coleção para o email '$email' existe: ${userDoc.exists()}")
            return@withContext userDoc.exists()
        } catch (e: Exception) {
            Log.e("UsersRepository", "Erro ao verificar se o documento usuário existe: ${e.message}")
            return@withContext false
        }
    }

    suspend fun verifyUserName(email: String): Pair<Boolean, String?> = withContext(Dispatchers.IO) {
        try {
            val userDoc = usersInitCollection(email).document("usuário").get().await()
            if (userDoc.exists()) {
                val userName = userDoc.getString("user_name")
                return@withContext Pair(!userName.isNullOrEmpty(), userName)
            }
            return@withContext Pair(false, null)
        } catch (e: Exception) {
            return@withContext Pair(false, null)
        }
    }

    suspend fun findUserName(email: String): String = withContext(Dispatchers.IO) {
        try {
            val userDoc = usersInitCollection(email).document("usuário").get().await()
            if (userDoc.exists()) {
                val userName = userDoc.getString("user_name")
                return@withContext userName ?: "Nome de usuário não encontrado"
            }
            "Nome de usuário não encontrado"
        } catch (e: Exception) {
            "Nome de usuário não encontrado"
        }
    }
}

fun DataUser.toDataUser() = DataUser(
    user_id = this.user_id,
    user_email = this.user_email,
    user_emailverified = this.user_emailverified,
    user_name = this.user_name
)
package br.com.dieyteixeira.placaruno.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.DataUser
import br.com.dieyteixeira.placaruno.repositories.UsersRepository
import br.com.dieyteixeira.placaruno.ui.states.UsersListUiState
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class UsersListViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UsersListUiState> =
        MutableStateFlow(UsersListUiState())
    val uiState get() = _uiState

    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    init {
        viewModelScope.launch {
            loadAllUsers()
        }
    }

    suspend fun loadAllUsers() {
        try {
            repository.loadAllUsers().collect { users ->
                val filteredUsers = users.filter { user ->
                    !user.user_email.isNullOrBlank()
                }
                _uiState.value = _uiState.value.copy(users = filteredUsers)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun save(email: String) {
        if (email.isNotBlank()) {
            try {
                val userDocumentExists  = repository.userDocumentExists(email)

                if (userDocumentExists ) {
                    Log.d("UsersListViewModel", "Coleção para o email '$email' já existe.")
                    return
                }

                val user = DataUser(
                    user_id = UUID.randomUUID().toString(),
                    user_email = email,
                    user_emailverified = true,
                    user_name = ""
                )

                repository.save(email, user)

            } catch (e: Exception) {

            }
        } else {

        }
    }
}
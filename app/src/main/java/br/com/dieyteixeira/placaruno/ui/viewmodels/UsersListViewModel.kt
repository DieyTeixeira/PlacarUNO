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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class UsersListViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UsersListUiState> =
        MutableStateFlow(UsersListUiState())
    val uiState get() = _uiState

    private val _shouldShowUserNameDialog = MutableStateFlow(false)
    val shouldShowUserNameDialog: StateFlow<Boolean> get() = _shouldShowUserNameDialog

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    init {
        viewModelScope.launch {
            loadAllUsers()
            checkAndShowUserNameDialog()
            loadUserName()
        }
    }

    suspend fun loadUserName() {
        userEmail?.let {
            _userName.value = findUserName(it)
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
    suspend fun saveSignIn(email: String) {
        if (email.isNotBlank()) {
            try {

                val userExists = repository.userDocumentExists(email)
                if (userExists) {
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

    suspend fun saveSignUp(email: String) {
        if (email.isNotBlank()) {
            try {

                val user = DataUser(
                    user_id = UUID.randomUUID().toString(),
                    user_email = email,
                    user_emailverified = false,
                    user_name = ""
                )

                repository.save(email, user)

            } catch (e: Exception) {

            }
        } else {

        }
    }

    suspend fun saveUserName(userName: String) {
        userEmail?.let { email ->
            if (email.isNotBlank() && userName.isNotBlank()) {
                try {
                    val user = DataUser(
                        user_id = UUID.randomUUID().toString(),
                        user_email = email,
                        user_emailverified = true,
                        user_name = userName
                    )
                    repository.save(email, user)
                    _shouldShowUserNameDialog.value = false
                } catch (e: Exception) {
                    Log.e("UsersListViewModel", "Erro ao salvar o nome de usuário: ${e.message}")
                }
            }
        }
    }

    private suspend fun checkAndShowUserNameDialog() {
        userEmail?.let { email ->
            val (isUserNameValid, _) = repository.verifyUserName(email)
            _shouldShowUserNameDialog.value = !isUserNameValid
        }
    }

    private suspend fun findUserName(email: String): String {
        return repository.findUserName(email)
    }

}
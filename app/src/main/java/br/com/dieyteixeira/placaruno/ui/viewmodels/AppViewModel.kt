package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.dieyteixeira.placaruno.authentication.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

data class AppState(
    val user: User? = null,
    val isInitLoading: Boolean = true
)

class AppViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state
        .combine(firebaseAuthRepository.currentUser) { appState, authResult ->
            val user = authResult.currentUser?.email?.let { User(it) }
            appState.copy(user = user, isInitLoading = authResult.isInitLoading)
        }

}
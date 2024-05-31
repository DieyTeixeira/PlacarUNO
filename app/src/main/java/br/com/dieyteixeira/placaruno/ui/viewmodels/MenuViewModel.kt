package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.dieyteixeira.placaruno.authentication.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.ui.states.MenuUiState
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class MenuViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MenuUiState> =
        MutableStateFlow(MenuUiState())


    val uiState
        get() = _uiState
            .combine(firebaseAuthRepository.currentUser) { uiState, authResult ->
                uiState.copy(user = authResult.currentUser?.email)
            }

    fun signOut() {
        firebaseAuthRepository.signOut()
    }
}
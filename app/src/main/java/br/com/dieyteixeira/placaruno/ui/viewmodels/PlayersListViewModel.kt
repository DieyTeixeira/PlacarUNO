package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.authentication.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.repositories.toPlayer
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayersListViewModel(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlayersListUiState> =
        MutableStateFlow(PlayersListUiState())
    val uiState
        get() = _uiState
            .combine(repository.players) { uiState, players ->
                uiState.copy(players = players.map { it.toPlayer() })
            }

}
package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.repositories.toPlayer
import br.com.dieyteixeira.placaruno.ui.states.PlayersEditUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PlayersEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlayersEditUiState> =
        MutableStateFlow(PlayersEditUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["playerId"]

    private val userEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTitleChange = { title ->
                    _uiState.update {
                        it.copy(title = title)
                    }
                },
                topAppBarTitle = "ADICIONAR"
            )
        }
        id?.let {
            loadPlayerData(it)
        }
    }

    private fun loadPlayerData(id: String) {
        userEmail?.let { email ->
            viewModelScope.launch {
                repository.findById(email, id)
                    .filterNotNull()
                    .mapNotNull { it.toPlayer() }
                    .collectLatest { player ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "EDITAR",
                                title = player.player_name,
                                isDeleteEnabled = true
                            )
                        }
                    }
            }
        }
    }

    suspend fun save() {
        userEmail?.let { email ->
            with(_uiState.value) {
                repository.save(
                    email,
                    Player(
                        player_id = id ?: UUID.randomUUID().toString(),
                        player_name = title
                    )
                )
            }
        }
    }

    suspend fun delete() {
        userEmail?.let { email ->
            id?.let {
                repository.delete(email, id)
            }
        }
    }
}
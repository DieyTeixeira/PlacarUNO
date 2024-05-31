package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.repositories.toPlayer
import br.com.dieyteixeira.placaruno.ui.states.PlayersEditUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PlayerFormViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlayersEditUiState> =
        MutableStateFlow(PlayersEditUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["playerId"]

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTitleChange = { title ->
                    _uiState.update {
                        it.copy(title = title)
                    }
                },
                topAppBarTitle = "ADICIONAR JOGADOR"
            )
        }
        id?.let {
            viewModelScope.launch {
                repository.findById(id)
                    .filterNotNull()
                    .mapNotNull {
                        it.toPlayer()
                    }.collectLatest { player ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "EDITAR",
                                title = player.title,
                                isDeleteEnabled = true
                            )
                        }
                    }
            }
        }
    }

    suspend fun save() {
        with(_uiState.value) {
            repository.save(
                Player(
                    id = id ?: UUID.randomUUID().toString(),
                    title = title
                )
            )
        }
    }

    suspend fun delete() {
        id?.let {
            repository.delete(id)
        }
    }
}
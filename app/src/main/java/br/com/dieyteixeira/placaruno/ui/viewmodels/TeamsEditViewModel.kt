package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.repositories.toTeam
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class TeamsEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: TeamsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TeamsEditUiState> =
        MutableStateFlow(TeamsEditUiState())
    val uiState = _uiState.asStateFlow()
    private val idT: String? = savedStateHandle["teamId"]

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTitleChange = { title ->
                    _uiState.update {
                        it.copy(title = title)
                    }
                },
                topAppBarTitle = "ADICIONAR EQUIPE"
            )
        }
        idT?.let {
            viewModelScope.launch {
                repository.findById(idT)
                    .filterNotNull()
                    .mapNotNull {
                        it.toTeam()
                    }.collectLatest { team ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "EDITAR",
                                title = team.titleT,
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
                Team(
                    idT = idT ?: UUID.randomUUID().toString(),
                    titleT = title
                )
            )
        }
    }

    suspend fun delete() {
        idT?.let {
            repository.delete(idT)
        }
    }
}
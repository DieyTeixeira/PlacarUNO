package br.com.dieyteixeira.placaruno.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.models.Team
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.repositories.toPlayer
import br.com.dieyteixeira.placaruno.repositories.toTeam
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import com.google.firebase.auth.FirebaseAuth
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
    private val id: String? = savedStateHandle["teamId"]

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
            loadTeamData(it)
        }
    }

    private fun loadTeamData(id: String) {
        userEmail?.let { email ->
            viewModelScope.launch {
                repository.findById(email, id)
                    .filterNotNull()
                    .mapNotNull { it.toTeam() }
                    .collectLatest { team ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "EDITAR",
                                title = team.team_name,
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
                    Team(
                        team_id = id ?: UUID.randomUUID().toString(),
                        team_name = title
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
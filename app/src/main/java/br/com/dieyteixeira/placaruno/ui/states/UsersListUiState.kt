package br.com.dieyteixeira.placaruno.ui.states

import br.com.dieyteixeira.placaruno.models.DataUser

data class UsersListUiState(
    val users: List<DataUser> = emptyList(),
    val title: String = ""
)
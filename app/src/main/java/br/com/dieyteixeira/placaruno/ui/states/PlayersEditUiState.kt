package br.com.dieyteixeira.placaruno.ui.states

data class PlayersEditUiState(
    val title: String = "",
    val topAppBarTitle: String = "",
    val onTitleChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false
)
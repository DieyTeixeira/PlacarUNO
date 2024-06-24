package br.com.dieyteixeira.placaruno.ui.states

data class PlayersEditUiState(
    val playerId: String? = null,
    val title: String = "",
    val topAppBarTitle: String = "",
    val onTitleChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false
)
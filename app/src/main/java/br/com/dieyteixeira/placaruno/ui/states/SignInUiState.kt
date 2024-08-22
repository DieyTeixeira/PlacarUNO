package br.com.dieyteixeira.placaruno.ui.states

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val passwordCharError: String? = null,
    val error: String? = null,
    val success: String? = null,
    val isShowPassword: Boolean = false,
    val isAuthenticated: Boolean = false,
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onTogglePasswordVisibility: () -> Unit = {},
    val successMessage: String? = null
)
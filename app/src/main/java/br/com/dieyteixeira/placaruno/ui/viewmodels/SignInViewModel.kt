package br.com.dieyteixeira.placaruno.ui.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dieyteixeira.placaruno.firebase.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.repositories.UsersRepository
import br.com.dieyteixeira.placaruno.ui.components.vibration
import br.com.dieyteixeira.placaruno.ui.states.SignInUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val repository: UsersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()
    private val _signInIsSucessful = MutableSharedFlow<Boolean>()
    val signInIsSucessful = _signInIsSucessful.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user ->
                    _uiState.update {
                        it.copy(email = user)
                    }
                },
                onPasswordChange = { password ->
                    val filteredPassword = password.filter { it.isLetterOrDigit() }
                    val passwordCharError = if (password != filteredPassword) {
                        "A senha não deve conter caracteres especiais\nApenas letras (A-Z) e números (0-9)"
                    } else {
                        null
                    }
                    _uiState.update {
                        it.copy(
                            password = password,
                            passwordCharError = passwordCharError
                        )
                    }
                },
                onTogglePasswordVisibility = {
                    _uiState.update {
                        it.copy(isShowPassword = !_uiState.value.isShowPassword)
                    }
                }
            )
        }
    }

    suspend fun signIn() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isEmpty()) {
            _uiState.update {
                it.copy(
                    error = "Por favor, insira seu email."
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update {
                it.copy(
                    error = "Formato de email inválido."
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
            return
        }

        if (password.isEmpty()) {
            _uiState.update {
                it.copy(
                    error = "Por favor, insira sua senha."
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
            return
        }

        try {

            firebaseAuthRepository.signIn(email, password)

            val currentUser = firebaseAuthRepository.getCurrentUser()

            if (currentUser != null && !currentUser.isEmailVerified) {
                firebaseAuthRepository.sendEmailVerification()
                firebaseAuthRepository.signOut()
                _uiState.update {
                    it.copy(
                        error = "Por favor, verifique seu e-mail antes de fazer login."
                    )
                }
                delay(3000)
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
                return
            }

        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e) {
                is FirebaseAuthInvalidUserException -> "Usuário não cadastrado"
                is FirebaseAuthInvalidCredentialsException -> "Senha incorreta"
                is FirebaseAuthEmailException -> "Email inválido"
                else -> "Erro ao fazer login"
            }

            Log.e("signInViewModel", "signIn: ", e)
            _uiState.update {
                it.copy(
                    error = errorMessage
                )
            }

            delay(3000)

            _uiState.update {
                it.copy(
                    error = null
                )
            }
        } catch (e: Exception) {
            Log.e("signInViewModel", "signIn: ", e)
            _uiState.update {
                it.copy(
                    error = "Usuário não cadastrado ou incorreto"
                )
            }

            delay(3000)

            _uiState.update {
                it.copy(
                    error = null
                )
            }
        }
    }

    suspend fun sendPasswordResetEmail() {
        val email = _uiState.value.email
        if (email.isEmpty()) {
            _uiState.update {
                it.copy(
                    error = "Por favor, insira o seu email."
                )
            }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) {
            _uiState.update {
                it.copy(
                    error = "Formato de email inválido."
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
            return
        }

        firebaseAuthRepository.sendPasswordResetEmail(
            email = email,
            onSuccess = {
                _uiState.update {
                    it.copy(
                        error = "Email de redefinição de senha enviado"
                    )
                }

                viewModelScope.launch {
                    delay(3000)
                    _uiState.update {
                        it.copy(
                            error = null
                        )
                    }
                }
            },
            onError = { errorMessage ->
                _uiState.update {
                    it.copy(
                        error = errorMessage
                    )
                }

                viewModelScope.launch {
                    delay(3000)
                    _uiState.update {
                        it.copy(
                            error = null
                        )
                    }
                }
            }
        )
    }
}
package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.states.PlayersEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun PlayersEditScreen(
    uiState: PlayersEditUiState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit = {},
) {

    /***** VARIÁVEIS *****/
    var isNameEmpty by remember { mutableStateOf(true) }
    val topAppBarTitle = uiState.topAppBarTitle
    val title = uiState.title
    val focusManager = LocalFocusManager.current

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(
            titleHeader = topAppBarTitle,
            backgroundColor = VerdeUno,
            icon = painterResource(id = R.drawable.ic_g_player)
        )

        /***** BOTÕES *****/
        GenericButtonBar(
            buttons = listOf(
                ButtonInfo(
                    icon = painterResource(id = R.drawable.ic_double_arrow_left),
                    description = "Back",
                    onClick = onBackClick
                ),  // Posição 1 botão
                null, // Posição 2 sem botão
                if (isNameEmpty) {
                    null // Posição 3 sem botão
                } else {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_save),
                        description = "Save",
                        onClick = {
                            focusManager.clearFocus()
                            onSaveClick()
                        }
                    ) // Posição 3 botão
                },
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )

        /***** CORPO DA ESTRUTURA *****/
        Column(
            Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        uiState.onTitleChange(it)
                        isNameEmpty = it.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 14.dp),
                    textStyle = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    label = {
                        Text(
                            text = "Nome do Jogador",
                            style = TextStyle.Default.copy(
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 12.sp
                            )
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                )
            }
        }
    }

    /***** RODAPÉ *****/
    Baseboard(color = Color.Transparent)

}

/***** VISUALIZAÇÃO ADICIONAR *****/
@Preview(showBackground = true)
@Composable
fun PlayersEditScreenPreview() {
    PlacarUNOTheme {
        PlayersEditScreen(
            uiState = PlayersEditUiState(
                topAppBarTitle = "CADASTRAR"
            ),
            onSaveClick = {}
        )
    }
}

/***** VISUALIZAÇÃO EDITAR *****/
@Preview(showBackground = true)
@Composable
fun PlayersEditScreenWithEditModePreview() {
    PlacarUNOTheme {
        PlayersEditScreen(
            uiState = PlayersEditUiState(
                topAppBarTitle = "EDITAR",
                isDeleteEnabled = true
            ),
            onSaveClick = {}
        )
    }
}
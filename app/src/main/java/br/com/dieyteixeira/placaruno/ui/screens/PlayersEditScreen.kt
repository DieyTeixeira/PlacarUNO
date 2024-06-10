package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.compscreens.Baseboard
import br.com.dieyteixeira.placaruno.ui.compscreens.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.compscreens.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.compscreens.Header
import br.com.dieyteixeira.placaruno.ui.states.PlayersEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun PlayersEditScreen(
    uiState: PlayersEditUiState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit = {},
) {

    /***** VARIÁVEIS *****/
    var isNameEmpty by remember { mutableStateOf(true) }
    val topAppBarTitle = uiState.topAppBarTitle
    val deleteEnabled = uiState.isDeleteEnabled
    val title = uiState.title
    val titleFontStyle = TextStyle.Default.copy(fontSize = 25.sp)
    val focusManager = LocalFocusManager.current

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Column {
            Header(titleHeader = topAppBarTitle)
        }

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
        Spacer(modifier = Modifier.height(5.dp))

        /***** CORPO DA ESTRUTURA *****/
        OutlinedTextField(
            value = title,
            onValueChange = {
                uiState.onTitleChange(it)
                isNameEmpty = it.isEmpty()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textStyle = titleFontStyle.copy(
                color = Color.White,
                fontSize = 22.sp
            ),
            label = {
                if (title.isEmpty()) {
                    Text(
                        text = "Nome",
                        style = titleFontStyle.copy(
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 22.sp
                        )
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
        )
    }

    /***** RODAPÉ *****/
    Baseboard()

}

/***** VISUALIZAÇÃO ADICIONAR *****/
@Preview(showBackground = true)
@Composable
fun PlayersEditScreenPreview() {
    PlacarUNOTheme {
        PlayersEditScreen(
            uiState = PlayersEditUiState(
                topAppBarTitle = "ADICIONAR JOGADOR"
            ),
            onSaveClick = {},
            onDeleteClick = {}
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
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}
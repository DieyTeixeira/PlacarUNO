package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import br.com.dieyteixeira.placaruno.ui.states.TeamsEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun TeamsEditScreen(
    uiState: TeamsEditUiState,
    modifier: Modifier = Modifier,
    onSaveTeamClick: () -> Unit,
    onBackClick: () -> Unit = {},
) {

    /***** VARIÁVEIS *****/
    var isNameEmpty by remember { mutableStateOf(true) }
    val topAppBarTitle = uiState.topAppBarTitle
    val title = uiState.title
    val titleFontStyle = TextStyle.Default.copy(fontSize = 25.sp)
    val focusManager = LocalFocusManager.current

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(
            titleHeader = topAppBarTitle,
            backgroundColor = AzulUno,
            icon = painterResource(id = R.drawable.ic_g_team)
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
                            onSaveTeamClick()
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
                        .height(57.dp)
                        .padding(horizontal = 14.dp),
                    textStyle = titleFontStyle.copy(
                        color = Color.White,
                        fontSize = 19.sp
                    ),
                    label = {
                        Text(
                            text = "Nome",
                            style = TextStyle.Default.copy(
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 15.sp
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
    Baseboard()

}

/***** VISUALIZAÇÃO ADICIONAR *****/
@Preview(showBackground = true)
@Composable
fun TeamsEditScreenPreview() {
    PlacarUNOTheme {
        TeamsEditScreen(
            uiState = TeamsEditUiState(
                topAppBarTitle = "CADASTRAR"
            ),
            onSaveTeamClick = {}
        )
    }
}

/***** VISUALIZAÇÃO EDITAR *****/
@Preview(showBackground = true)
@Composable
fun TeamsEditScreenWithEditModePreview() {
    PlacarUNOTheme {
        TeamsEditScreen(
            uiState = TeamsEditUiState(
                topAppBarTitle = "EDITAR",
                isDeleteEnabled = true
            ),
            onSaveTeamClick = {}
        )
    }
}
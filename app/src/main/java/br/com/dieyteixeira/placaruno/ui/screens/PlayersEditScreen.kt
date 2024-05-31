package br.com.dieyteixeira.placaruno.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.collection.emptyLongSet
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.compscreens.Baseboard
import br.com.dieyteixeira.placaruno.ui.compscreens.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.compscreens.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.compscreens.Header
import br.com.dieyteixeira.placaruno.ui.states.PlayersEditUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

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
                    icon = painterResource(id = R.drawable.ic_back_br),
                    description = "Back",
                    onClick = onBackClick
                ),  // Posição 1 botão
                null, // Posição 2 sem botão
                if (isNameEmpty) {
                    null // Posição 3 sem botão
                } else {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_save_br),
                        description = "Save",
                        onClick = {
                            focusManager.clearFocus()
                            onSaveClick()
                        }
                    ) // Posição 3 botão
                },
                null, // Posição 4 sem botão
                if (deleteEnabled) {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_delete_br),
                        description = "Delete",
                        onClick = onDeleteClick
                    ) // Posição 1 botão
                } else {
                    null // Posição 1 sem botão
                },
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
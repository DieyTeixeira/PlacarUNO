package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.rotationMatrix
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.compscreens.Baseboard
import br.com.dieyteixeira.placaruno.ui.compscreens.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.compscreens.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.compscreens.Header
import br.com.dieyteixeira.placaruno.ui.states.MenuUiState
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

enum class ButtonLayout {
    COLUMN,
    ROW,
    GRID_2x2
}

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun MenuScreen(
    uiState: MenuUiState,
    modifier: Modifier = Modifier,
    onPlayersClick: () -> Unit,
    onTeamsClick: () -> Unit,
    onNewGameClick: () -> Unit,
    onScoreboardClick: () -> Unit,
    onExitToAppClick: () -> Unit
) {

    var layoutMode by remember { mutableStateOf(false) }
    var buttonLayout by remember { mutableStateOf(ButtonLayout.COLUMN) }

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(titleHeader = "PLACAR UNO")

        /***** BOTÕES *****/
        GenericButtonBar(
            buttons = listOf(

                ButtonInfo(
//                    icon = if (layoutMode) painterResource(id = R.drawable.ic_add) else painterResource(id = R.drawable.ic_xis),
                    icon = painterResource(id = R.drawable.ic_add),
                    description = "Mudar Layout",
                    onClick = { layoutMode = !layoutMode },
                    debounce = false,
                    rotate = layoutMode
                ), // Posição 1 botão X

                if (layoutMode == true) {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_layout_column),
                        description = "Mudar Layout para Coluna",
                        onClick = { buttonLayout = ButtonLayout.COLUMN }
                    )
                } else {
                    null
                }, // Posição 2 sem botão

                if (layoutMode == true) {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_layout_row),
                        description = "Mudar Layout para Linha",
                        onClick = { buttonLayout = ButtonLayout.ROW }
                    )
                } else {
                    null
                }, // Posição 3 sem botão

                if (layoutMode == true) {
                    ButtonInfo(
                        icon = painterResource(id = R.drawable.ic_layout_2x2),
                        description = "Mudar Layout para 2x2",
                        onClick = { buttonLayout = ButtonLayout.GRID_2x2 }
                    )
                } else {
                    null
                }, // Posição 4 sem botão

                ButtonInfo(
                    icon = painterResource(id = R.drawable.ic_out),
                    description = "Sair",
                    onClick = onExitToAppClick
                )  // Posição 5 botão Sair

            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(
                text = "Bem vindo(a), ",
                style = TextStyle(
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
        Row {
            Text(
                text = "${uiState.user}",
                style = TextStyle(
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(18.dp))

        /***** CORPO DA ESTRUTURA *****/
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState())
                .animateContentSize(),
        ) {
            when (buttonLayout) {
                ButtonLayout.COLUMN ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        ColorButton(color = VerdeUno, text = "JOGADOR", onClick = onPlayersClick)
                        ColorButton(color = AzulUno, text = "EQUIPE", onClick = onTeamsClick)
                        ColorButton(color = VermelhoUno, text = "NOVO JOGO", onClick = onNewGameClick)
                        ColorButton(color = AmareloUno, text = "PLACAR", onClick = onScoreboardClick)
                    }
                ButtonLayout.ROW ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) { ColorButton(color = VerdeUno, text = "JOGADOR", onClick = onPlayersClick, height = 180.dp, showText = false) }
                        Box(modifier = Modifier.weight(1f)) { ColorButton(color = AzulUno, text = "EQUIPE", onClick = onTeamsClick, height = 180.dp, showText = false) }
                        Box(modifier = Modifier.weight(1f)) { ColorButton(color = VermelhoUno, text = "NOVO JOGO", onClick = onNewGameClick, height = 180.dp, showText = false) }
                        Box(modifier = Modifier.weight(1f)) { ColorButton(color = AmareloUno, text = "PLACAR", onClick = onScoreboardClick, height = 180.dp, showText = false) }
                    }
                ButtonLayout.GRID_2x2 ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Row {
                            Box(modifier = Modifier.weight(1f)) { ColorButton(color = VerdeUno, text = "JOGADOR", onClick = onPlayersClick, height = 150.dp) }
                            Spacer(modifier = Modifier.width(15.dp))
                            Box(modifier = Modifier.weight(1f)) { ColorButton(color = AzulUno, text = "EQUIPE", onClick = onTeamsClick, height = 150.dp) }
                        }
                        Row {
                            Box(modifier = Modifier.weight(1f)) { ColorButton(color = VermelhoUno, text = "NOVO JOGO", onClick = onNewGameClick, height = 150.dp) }
                            Spacer(modifier = Modifier.width(15.dp))
                            Box(modifier = Modifier.weight(1f)) { ColorButton(color = AmareloUno, text = "PLACAR", onClick = onScoreboardClick, height = 150.dp) }
                        }
                    }

            }
        }


        Spacer(modifier = Modifier.height(18.dp))

        //***** RODAPÉ *****
        Baseboard()
    }
}

/***** BOTÕES *****/
@Composable
fun ColorButton(
    color: Color,
    text: String,
    onClick: () -> Unit,
    showText: Boolean = true,
    height: Dp = 110.dp,
    verticalText: Boolean = false,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height) // Ajustar altura do botão
            .background(
                color = color,
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    bottomStart = 5.dp,
                    topEnd = 5.dp,
                    bottomEnd = 30.dp
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = if (showText) Alignment.Center else Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = when (text) {
                    "JOGADOR" -> painterResource(R.drawable.ic_g_player)
                    "EQUIPE" -> painterResource(R.drawable.ic_g_team)
                    "NOVO JOGO" -> painterResource(R.drawable.ic_g_new_game)
                    "PLACAR" -> painterResource(R.drawable.ic_g_score)
                    else -> painterResource(R.drawable.ic_xis)
                },
                contentDescription = null,
                tint = Color.White,
                modifier = if (showText) {
                    Modifier
                        .size(60.dp)
                        .padding(top = 5.dp)
                } else {
                    Modifier
                        .size(80.dp)
                        .padding(top = 30.dp)
                }
            )
            if (showText) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = if (verticalText) 10.sp else 25.sp,
                    modifier = if (verticalText) {
                        Modifier
                            .rotate(90f)
                            .padding(bottom = 5.dp)
                    } else {
                        Modifier.padding(top = 5.dp)
                    }
                )
            }
        }
    }
}

/***** VISUALIZAÇÃO *****/
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PlacarUNOTheme {
        MenuScreen (
            uiState = MenuUiState(),
            onPlayersClick = {},
            onTeamsClick = {},
            onNewGameClick = {},
            onScoreboardClick = {},
            onExitToAppClick = {}
        )
    }
}
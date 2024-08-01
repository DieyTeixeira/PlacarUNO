package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.firebase.currentVersionName
import br.com.dieyteixeira.placaruno.ui.components.Baseboard
import br.com.dieyteixeira.placaruno.ui.components.FabWithSubButtons
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.components.PreferenceManager
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
    backgroundColor: Color = Color.Gray.copy(alpha = 0.3f),
    onPlayersClick: () -> Unit,
    onTeamsClick: () -> Unit,
    onNewGameClick: () -> Unit,
    onScoreboardClick: () -> Unit,
    onExitToAppClick: () -> Unit
) {

    val context = LocalContext.current
    var buttonLayout by remember { mutableStateOf(PreferenceManager.getSavedLayout(context)) }

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {

        /***** CABEÇALHO *****/
        Header(
            titleHeader = "PLACAR UNO",
            backgroundColor = Color.Black,
            icon = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(55.dp)
                    .background(color = backgroundColor)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    FabWithSubButtons(
                        onSubButton1Click = {
                            buttonLayout = ButtonLayout.COLUMN
                            PreferenceManager.saveLayout(context, ButtonLayout.COLUMN)
                        },
                        onSubButton2Click = {
                            buttonLayout = ButtonLayout.ROW
                            PreferenceManager.saveLayout(context, ButtonLayout.ROW)
                        },
                        onSubButton3Click = {
                            buttonLayout = ButtonLayout.GRID_2x2
                            PreferenceManager.saveLayout(context, ButtonLayout.GRID_2x2)
                        }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_out),
                        contentDescription = "Sair",
                        modifier = Modifier
                            .size(34.dp)
                            .clickable { onExitToAppClick() }
                            .padding(end = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(
                text = "Bem vindo(a), ",
                style = TextStyle(
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
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
//            when (buttonLayout) {
//                ButtonLayout.COLUMN ->
            AnimatedVisibility(visible = buttonLayout == ButtonLayout.COLUMN, enter = fadeIn()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    ColorButton(color = VerdeUno, text = "JOGADOR", onClick = onPlayersClick)
                    ColorButton(color = AzulUno, text = "EQUIPE", onClick = onTeamsClick)
                    ColorButton(color = VermelhoUno, text = "NOVO JOGO", onClick = onNewGameClick)
                    ColorButton(color = AmareloUno, text = "PLACAR", onClick = onScoreboardClick)
                }
            }
//                ButtonLayout.ROW ->
            AnimatedVisibility(visible = buttonLayout == ButtonLayout.ROW, enter = fadeIn()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) { ColorButton(color = VerdeUno, text = "JOGADOR", onClick = onPlayersClick, height = 180.dp, showText = false) }
                    Box(modifier = Modifier.weight(1f)) { ColorButton(color = AzulUno, text = "EQUIPE", onClick = onTeamsClick, height = 180.dp, showText = false) }
                    Box(modifier = Modifier.weight(1f)) { ColorButton(color = VermelhoUno, text = "NOVO JOGO", onClick = onNewGameClick, height = 180.dp, showText = false) }
                    Box(modifier = Modifier.weight(1f)) { ColorButton(color = AmareloUno, text = "PLACAR", onClick = onScoreboardClick, height = 180.dp, showText = false) }
                }
            }
//                ButtonLayout.GRID_2x2 ->
            AnimatedVisibility(visible = buttonLayout == ButtonLayout.GRID_2x2, enter = fadeIn()) {
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
        Baseboard(color = Color.LightGray)
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
                modifier = Modifier
                    .size(if (showText) 60.dp else 80.dp)
                    .padding(top = if (showText) 5.dp else 30.dp)
            )
            if (showText) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 25.sp,
                    ),
                    modifier = Modifier.padding(top = 5.dp)
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
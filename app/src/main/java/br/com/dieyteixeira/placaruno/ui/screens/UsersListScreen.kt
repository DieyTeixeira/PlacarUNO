package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.models.Player
import br.com.dieyteixeira.placaruno.ui.components.ButtonInfo
import br.com.dieyteixeira.placaruno.ui.components.GenericButtonBar
import br.com.dieyteixeira.placaruno.ui.components.Header
import br.com.dieyteixeira.placaruno.ui.states.PlayersListUiState
import br.com.dieyteixeira.placaruno.ui.states.UsersListUiState
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.UsersListViewModel

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun UsersListScreen(
    uiState: UsersListUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: UsersListViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.loadAllUsers()
    }

    Column(
        Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {
        /***** CABEÇALHO *****/
        Header(
            titleHeader = "LISTA DE USUÁRIOS",
            backgroundColor = Color.Gray,
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
                null, // Posição 3 sem botão
                null, // Posição 4 sem botão
                null, // Posição 5 sem botão
            ),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(5.dp))

        /***** CORPO DA ESTRUTURA *****/
        Box(modifier) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.945f)
            ) {
                itemsIndexed(uiState.users) { index, users ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            Modifier.padding(5.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
//                                    .height(45.dp)
                                    .background(
                                        color = Color(0xFF393F42),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Column {
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp),
                                    ){
                                        Text(
                                            text = users.user_email,
                                            style = TextStyle.Default.copy(
                                                fontSize = 18.sp,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .weight(1f)
                                        )
                                    }
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 20.dp, bottom = 5.dp, top = 5.dp ),
                                    ){
                                        Text(
                                            text = ">> Nome: " + if (users.user_name == null) "Não informado" else users.user_name,
                                            style = TextStyle.Default.copy(
                                                fontSize = 16.sp,
                                                color = Color.White,
                                                fontStyle = FontStyle.Italic
                                            ),
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .weight(1f)
                                        )
                                    }
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 20.dp, bottom = 5.dp, top = 5.dp )
                                    ){
                                        Text(
                                            text = ">> Email Verificado: " + if (users.user_emailverified == true) "Sim" else "Não",
                                            style = TextStyle.Default.copy(
                                                fontSize = 16.sp,
                                                color = Color.White,
                                                fontStyle = FontStyle.Italic
                                            ),
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R

// Definição de componente - ** BARRA DOS BOTÕES **
@Composable
fun GamePlace(){

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ){

        /* C1 - (R1-R5) */
        Column (
            modifier = Modifier.weight(1f)
        ){
            /* C1 - R1 */
            Row (
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ){
                Column (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ){
                    Text(
                        text = "A1",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
            /* C1 - R2 */
            Row (
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ){
                Column (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_g_team),
                        contentDescription = "Jogador 1",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 5.dp)
                    )
                    Text(
                        text = "A2",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
            /* C1 - R3 */
            Row (
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ){
                Column (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_g_team),
                        contentDescription = "Jogador 1",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 5.dp)
                    )
                    Text(
                        text = "A3",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
            /* C1 - R4 */
            Row (
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ){
                Column (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_g_team),
                        contentDescription = "Jogador 1",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 5.dp)
                    )
                    Text(
                        text = "A4",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
            /* C1 - R5 */
            Row (
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ){
                Column (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ){
                    Text(
                        text = "A1",
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        }

        /* C2 - (R1-R5) */
        Column (
            modifier = Modifier.weight(1f)
        ){
            /* C2 - R1 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "B1",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C2 - R2 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "B2",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C2 - R3 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "B3",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C2 - R4 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "B4",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C2 - R5 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "B5",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
        }

        /* C3 - (R1-R5) */
        Column (
            modifier = Modifier.weight(1f)
        ){
            /* C3 - R1 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "C1",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C3 - R2 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "C2",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C3 - R3 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "C3",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C3 - R4 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "C4",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C3 - R5 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "C5",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
        }

        /* C4 - (R1-R5) */
        Column (
            modifier = Modifier.weight(1f)
        ){
            /* C4 - R1 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "D1",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C4 - R2 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "D2",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C4 - R3 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "D3",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C4 - R4 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "D4",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C4 - R5 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "D5",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
        }

        /* C5 - (R1-R5) */
        Column (
            modifier = Modifier.weight(1f)
        ){
            /* C5 - R1 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "E1",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C5 - R2 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "E2",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C5 - R3 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "E3",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C5 - R4 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "E4",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
            /* C5 - R5 */
            Row (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "E5",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
        }
    }

}

// Visualização
@Preview
@Composable
fun GamePlacePreview(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(color = Color(0xFF000000))
    ) {
        GamePlace()
    }
}
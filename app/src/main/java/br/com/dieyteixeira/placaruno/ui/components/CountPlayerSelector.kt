package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

@Composable
fun CountPlayerSelector(
    playerCount: Int,
    onPlayerCountChange: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .height(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .background(
                            color = VermelhoUno,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                bottomStart = 8.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                ) {
                    IconButton(
                        onClick = {
                            if (playerCount > 2) {
                                onPlayerCountChange(playerCount - 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            tint = Color.White,
                            contentDescription = "Decrease"
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(66.dp)
                        .background(
                            color = Color.Gray
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = playerCount.toString(),
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 18.sp
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .background(
                            color = VermelhoUno,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                ) {
                    IconButton(
                        onClick = {
                            if (playerCount < 8) {
                                onPlayerCountChange(playerCount + 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            tint = Color.White,
                            contentDescription = "Increase"
                        )
                    }
                }
            }
        }
    }
}

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun PlayerCountScreen(
//    playerCount: Int,
//    onPlayerCountChange: (Int) -> Unit
//) {
//    Scaffold(
//        backgroundColor = Color.Transparent,
//        content = {
//            CountSelector(
//                playerCount = playerCount,
//                onPlayerCountChange = onPlayerCountChange
//            )
//        }
//    )
//}
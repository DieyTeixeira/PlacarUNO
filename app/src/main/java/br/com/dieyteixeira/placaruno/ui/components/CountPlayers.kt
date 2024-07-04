package br.com.dieyteixeira.placaruno.ui.components

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno

@Composable
fun PlayerCountSelector(
    playerCount: Int,
    onPlayerCountChange: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Número de Jogadores",
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
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
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = playerCount.toString(),
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.width(16.dp))
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlayerCountScreen(
    playerCount: Int,
    onPlayerCountChange: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .height(150.dp)
    ) {
        Scaffold(
            backgroundColor = Color.Transparent,
            content = {
                PlayerCountSelector(
                    playerCount = playerCount,
                    onPlayerCountChange = onPlayerCountChange
                )
            }
        )
    }
}
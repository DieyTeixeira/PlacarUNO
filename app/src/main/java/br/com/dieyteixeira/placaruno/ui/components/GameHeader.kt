package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definição de componente - ** CABEÇALHO **
@Composable
fun NewGameHeader(
    titleHeader: String,
) {
    Spacer(modifier = Modifier.height(5.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    bottomStart = 8.dp,
                    topEnd = 8.dp,
                    bottomEnd = 8.dp
                )
            )
    ) {
        Text(
            text = titleHeader,
            style = TextStyle.Default.copy(
                color = Color.DarkGray,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
        )
    }
}
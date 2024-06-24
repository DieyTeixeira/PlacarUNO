package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R

// Definição de componente - ** CABEÇALHO **
@Composable
fun Header(
    titleHeader: String,
    backgroundColor: Color,
    icon: Painter? = null
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(5.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    bottomStart = 5.dp,
                    topEnd = 5.dp,
                    bottomEnd = 30.dp
                )
            )
            .padding(5.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .height(45.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(modifier = Modifier.weight(1f))
                icon?.let {
                    Image(
                        painter = icon,
                        contentDescription = null, // Descrição da imagem
                        modifier = Modifier
                            .size(55.dp)
                            .padding(end = 18.dp)
                    )
                }
                Text(
                    text = titleHeader,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 30.sp
                    ),
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

// Visualização
@Preview
@Composable
fun HeaderPreview(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(color = Color(0xFF000000))
    ) {
        Header(
            titleHeader = "Teste",
            backgroundColor = Color.Gray,
            icon = painterResource(id = R.drawable.ic_g_player)
        )
    }
}
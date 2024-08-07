package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.firebase.currentVersionName
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

// Definição de componente - ** RODAPÉ **
@Composable
fun Baseboard(
    color: Color
){
    Column {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF000000))
                .height(5.dp)
        ) {}
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
        ) {
            val colors = listOf(
                VerdeUno,
                AzulUno,
                VermelhoUno,
                AmareloUno
            )
            val sectionWidth = size.width / colors.size
            colors.forEachIndexed { index, color ->
                drawRect(
                    color = color,
                    topLeft = Offset(index * sectionWidth, 0f),
                    size = Size(sectionWidth, size.height)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF000000)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logodt_branco),
                    contentDescription = "Logo",
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Diey Teixeira",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text ="V. $currentVersionName",
                    style = TextStyle(
                        color = color,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

// Visualização
@Preview
@Composable
fun BaseboardPreview(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(color = Color(0xFF000000))
    ) {
        Baseboard(color = Color.LightGray)
    }
}
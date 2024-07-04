package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R

@Composable
fun PokerTable(playersCount: Int) {

    val playerImage = ImageBitmap.imageResource(id = R.drawable.ic_p_player)

    Box(
        modifier = Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
            .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawTable()
            drawPlayers(
                playersCount = playersCount,
                playerImage = playerImage
            )
        }
    }
}

private fun DrawScope.drawTable() {
    drawCircle(
        color = Color.DarkGray,
        center = Offset(size.width / 2f, size.height / 2f),
        radius = 280f
    )
}

private fun DrawScope.drawPlayers(playersCount: Int, playerImage: ImageBitmap) {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val radius = size.width * 0.4f
    val startAngle = -Math.PI / 2
    val distanceX = 96.dp.toPx()
    val distanceY = 80.dp.toPx()

    for (i in 0 until playersCount) {
        val angle = startAngle + (2 * Math.PI * i / playersCount)
        val playerX = centerX + radius * Math.cos(angle).toFloat()
        val playerY = centerY + radius * Math.sin(angle).toFloat()

        drawCircle(
            color = Color.Transparent,
            center = Offset(playerX, playerY),
            radius = 100f
        )

        drawImage(
            image = playerImage,
            topLeft = Offset(playerX - distanceX / 2, playerY - distanceY / 2),
            alpha = 1f, // Opacidade da imagem
            colorFilter = ColorFilter.tint(Color.LightGray), // Filtro de cor
        )
    }
}

@Preview
@Composable
fun PreviewPokerTable() {
    MaterialTheme {
        PokerTable(playersCount = 8)
    }
}
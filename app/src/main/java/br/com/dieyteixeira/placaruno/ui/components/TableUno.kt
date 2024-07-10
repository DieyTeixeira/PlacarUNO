package br.com.dieyteixeira.placaruno.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

@Composable
fun PokerTable(playersTotalCount: Int) {

    val playerImage = ImageBitmap.imageResource(id = R.drawable.ic_p_player)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawTable()
            drawPlayers(
                playersTotalCount = playersTotalCount,
                playerImage = playerImage
            )
        }
    }
}

private fun DrawScope.drawTable() {
    val colors = listOf(VermelhoUno, AzulUno, AmareloUno, VerdeUno)
    val segmentAngle = 360f / colors.size
    val strokeWidth = 5.dp.toPx()

    for (i in colors.indices) {
        drawArc(
            color = colors[i].copy(alpha = 0.9f),
            startAngle = i * segmentAngle,
            sweepAngle = segmentAngle,
            useCenter = false,
            topLeft = Offset((size.width - 500f) / 2, (size.height - 500f) / 2),
            size = Size(500f, 500f),
            style = Stroke(width = strokeWidth)
        )
    }

    drawCircle(
        color = Color.DarkGray,
        center = Offset(size.width / 2f, size.height / 2f),
        radius = 245f
    )
}

private fun DrawScope.drawPlayers(playersTotalCount: Int, playerImage: ImageBitmap) {
    val centerX = size.width / 2f
    val centerY = size.height / 2f - 20
    val radius = size.width * 0.37f
    val startAngle = -Math.PI / 2
    val distanceX = 82.dp.toPx()
    val distanceY = 70.dp.toPx()

    for (i in 0 until playersTotalCount) {
        val angle = startAngle + (-2 * Math.PI * i / playersTotalCount)
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

        val text = "Player ${i + 1}"
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text,
                playerX - distanceX / 2 + 48,
                playerY + distanceY / 2 + 5, // Adjust Y position for text below the image
                Paint().apply {
                    isAntiAlias = true
                    color = Color.LightGray.toArgb()
                    textSize = 35f // Adjust text size as needed
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewPokerTable() {
    MaterialTheme {
        PokerTable(playersTotalCount = 8)
    }
}
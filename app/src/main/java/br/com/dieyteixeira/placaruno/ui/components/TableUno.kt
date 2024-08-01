package br.com.dieyteixeira.placaruno.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.AzulUno
import br.com.dieyteixeira.placaruno.ui.theme.VerdeUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerOrTeam
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel

@Composable
fun PokerTable(
    playersTotalCount: Int,
    selectedPlayers: List<String>,
    scoreboardEditViewModel: ScoreboardEditViewModel = viewModel()
) {

    val playerImage = ImageBitmap.imageResource(id = R.drawable.ic_p_player)

    val playerCount by scoreboardEditViewModel.playerCount.collectAsState()

    val verificatePlayers by scoreboardEditViewModel.verificatePlayers.collectAsState()

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
                playerImage = playerImage,
                selectedPlayers = selectedPlayers,
                colorsCount = playerCount,
                verificatePlayers = verificatePlayers
            )
        }
    }
}

private fun DrawScope.drawTable() {
    val segmentAngle = 360f
    val strokeWidth = 5.dp.toPx()

    drawArc(
        color = Color.LightGray.copy(alpha = 0.5f),
        startAngle = segmentAngle,
        sweepAngle = segmentAngle,
        useCenter = false,
        topLeft = Offset((size.width - 500f) / 2, (size.height - 500f) / 2),
        size = Size(500f, 500f),
        style = Stroke(width = strokeWidth)
    )

    drawCircle(
        color = Color.DarkGray,
        center = Offset(size.width / 2f, size.height / 2f),
        radius = 245f
    )
}

private fun DrawScope.drawPlayers(
    playersTotalCount: Int,
    playerImage: ImageBitmap,
    selectedPlayers: List<String>,
    colorsCount: Int,
    verificatePlayers: PlayerOrTeam
) {

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

        // Lista completa de cores
        val allColors = listOf(VerdeUno, AzulUno, VermelhoUno, AmareloUno)

        // Filtrar as cores com base no número de equipes
        val colors = allColors.take(colorsCount)

        // Determine a cor do box com base no índice
        val boxColor = when (verificatePlayers) {
            PlayerOrTeam.PLAYERS -> Color.LightGray
            PlayerOrTeam.TEAMS -> colors[i % colors.size]
        }

        drawImage(
            image = playerImage,
            topLeft = Offset(playerX - distanceX / 2, playerY - distanceY / 2),
            alpha = 1f, // Opacidade da imagem
            colorFilter = ColorFilter.tint(boxColor), // Filtro de cor
        )

        val text = selectedPlayers.getOrNull(i) ?: ""
        val textPaint = Paint().apply {
            isAntiAlias = true
            color = Color.LightGray.toArgb()
            textSize = 35f // Ajuste o tamanho do texto conforme necessário
        }

        // Medir a largura e altura do texto para centralizá-lo no box
        val textWidth = textPaint.measureText(text)
        val textHeight = 35f // Altura do texto (ajustar se necessário)

        drawIntoCanvas {
            val canvas = it.nativeCanvas

            // Desenhe o box colorido ao redor do texto
            canvas.drawRect(
                playerX - distanceX / 2 + 60,
                playerY + distanceY / 2 - 37,
                playerX - distanceX / 2 + 160,
                playerY + distanceY / 2 - 33,
                Paint().apply {
                    color = boxColor.toArgb() // Cor do box
                }
            )

            // Desenhe o texto sobre o box
            canvas.drawText(
                text,
                playerX - distanceX / 2 + 112 - textWidth / 2,
                playerY + distanceY / 2 + 5, // Ajuste Y para centralizar o texto no box
                textPaint
            )
        }
    }
}

@Preview
@Composable
fun PreviewPokerTable() {
    MaterialTheme {
        PokerTable(
            playersTotalCount = 8,
            selectedPlayers = listOf("Dieinison", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hank")
        )
    }
}
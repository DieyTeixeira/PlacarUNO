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
import androidx.compose.ui.graphics.drawscope.withTransform
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
import com.google.android.exoplayer2.util.Log

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
    val strokeWidth = size.width * 0.01f
    val mult = 0.95f

    // BORDA MESA (ARCO CONTORNO)
//    drawArc(
//        color = Color.LightGray.copy(alpha = 0.5f),
//        startAngle = segmentAngle,
//        sweepAngle = segmentAngle,
//        useCenter = false,
//        topLeft = Offset(
//            (size.width - (size.width * mult) * 0.480f) / 2,
//            (size.height - (size.height * mult) * 0.375f) / 2
//        ),
//        size = Size(
//            (size.width * mult) * 0.48f,
//            (size.width * mult) * 0.48f
//        ),
//        style = Stroke(width = strokeWidth)
//    )

    Log.d("PokerTable", "size.width: ${size.width}")
    Log.d("PokerTable", "size.height: ${size.height}")

    // MESA (CÍRCULO CENTRAL)
    drawCircle(
        color = Color.Gray,
        center = Offset(
            size.width / 2f,
            size.height / 2f
        ),
        radius = (size.width * mult) * 0.235f
    )
}

private fun DrawScope.drawPlayers(
    playersTotalCount: Int,
    playerImage: ImageBitmap,
    selectedPlayers: List<String>,
    colorsCount: Int,
    verificatePlayers: PlayerOrTeam
) {
    val maxTextWidth = size.width * 0.205f
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val radius = size.width * 0.37f
    val startAngle = -Math.PI / 2
    val distanceX = size.width * 0.207f
    val distanceY = size.height * 0.127f // 0.12f > 0.125f

    val scale = size.width * 0.22f / playerImage.width // 0.20f
    Log.d("PokerTable", "playerImage.widht: ${playerImage.width}")

    val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.LightGray.toArgb()
        textSize = 35f // Ajuste o tamanho do texto conforme necessário
        textAlign = Paint.Align.CENTER
    }

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

        withTransform({
            translate(
                playerX - (playerImage.width * scale) / 2 + size.width * 0.03f,
                playerY - (playerImage.height * scale) / 2 + size.height * 0.010f
            )

            scale(scale, scale)
        }) {
            drawImage(
                image = playerImage,
                topLeft = Offset.Zero,
                alpha = 1f,
                colorFilter = ColorFilter.tint(boxColor)
            )
        }

        var text = selectedPlayers.getOrNull(i) ?: ""
        if (textPaint.measureText(text) > maxTextWidth) {
            while (textPaint.measureText("$text...") > maxTextWidth && text.isNotEmpty()) {
                text = text.dropLast(1)
            }
            text = "$text..."
        }

        drawIntoCanvas {
            val canvas = it.nativeCanvas

            // Desenhe o box colorido ao redor do texto
            canvas.drawRect(
                playerX - distanceX / 2 + size.width * 0.053f,
                playerY + distanceY / 2 - size.height * 0.028f,
                playerX - distanceX / 2 + size.width * 0.154f,
                playerY + distanceY / 2 - size.height * 0.024f,
                Paint().apply {
                    color = boxColor.toArgb()
                }
            )

            // Desenhe o texto sobre o box
            canvas.drawText(
                text,
                playerX - distanceX / 2 + size.width * 0.203f - maxTextWidth / 2,
                playerY + distanceY / 2 + size.height * 0.007f, // 0.035f > 0.025f
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
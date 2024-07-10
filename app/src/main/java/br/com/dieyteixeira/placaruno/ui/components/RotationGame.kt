package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

@Composable
fun RotationGame() {
    var isMirrored by remember { mutableStateOf(false) } // Estado para controlar se a imagem está espelhada
    val transition = updateTransition(targetState = isMirrored, label = "")

    val animatedMirrored by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                tween(durationMillis = 500)
            } else {
                tween(durationMillis = 500)
            }
        },
        label = ""
    ) { mirrored ->
        if (mirrored) -1f else 1f
    }

    var rotationAngle by remember { mutableStateOf(0f) } // Ângulo de rotação
    var rotationAngleTarget by remember { mutableStateOf(0f) } // Ângulo de rotação
    val infiniteTransition = rememberInfiniteTransition()
    val animatedRotationAngle by infiniteTransition.animateFloat(
        initialValue = rotationAngle,
        targetValue = rotationAngleTarget,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val currentIsMirrored by rememberUpdatedState(isMirrored)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_logo_placar_uno),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .padding(10.dp)
                .graphicsLayer {
                    alpha = 0.5f
                },
        )

        Image(
            painter = painterResource(id = R.drawable.ic_rotation2),
            contentDescription = "",
            modifier = Modifier
                .size(170.dp)
                .padding(10.dp)
                .clickable {
                    // Alterna o estado de espelhamento horizontal
                    isMirrored = !isMirrored
                    rotationAngle = if (currentIsMirrored) 360f else 0f
                    rotationAngleTarget = if (currentIsMirrored) 0f else 360f
                }
                .graphicsLayer {
                    // Aplica uma transformação de matriz para espelhar horizontalmente a imagem
                    scaleX = animatedMirrored
                    rotationZ = animatedRotationAngle
                },
            colorFilter = ColorFilter.tint(LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRotationGame() {
    RotationGame()
}
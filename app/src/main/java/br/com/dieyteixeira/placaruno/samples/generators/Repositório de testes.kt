/*

--1-------- BOTÃO DE GIRO INFINITO PARA SENTIDO DO UNO ---------------------------------------------

var isMirrored by remember { mutableStateOf(false) } // Estado para controlar se a imagem está espelhada
val transition = updateTransition(targetState = isMirrored, label = "")

val animatedMirrored by transition.animateFloat(
    transitionSpec = {
        if (targetState) {
            tween(durationMillis = 500)
        } else {
            tween(durationMillis = 500)
        }
    }
) { isMirrored ->
    if (isMirrored) -1f else 1f
}
var rotationAngle by remember { mutableStateOf(0f) } // Ângulo de rotação
var rotationAngleTarget by remember { mutableStateOf(0f) } // Ângulo de rotação
val animatedRotationAngle by rememberInfiniteTransition().animateFloat(
    initialValue = rotationAngle,
    targetValue = rotationAngleTarget,
    animationSpec = infiniteRepeatable(
        animation = tween(durationMillis = 1500),
        repeatMode = RepeatMode.Restart
    )
)
Image(
painter = painterResource(id = R.drawable.ic_rotation),
contentDescription = "Ícone horizontal",
modifier = Modifier
.size(60.dp)
.padding(16.dp)
.clickable {
    // Alterna o estado de espelhamento horizontal
    isMirrored = !isMirrored
    rotationAngle = if (isMirrored) 360f else 0f
    rotationAngleTarget = if (isMirrored) 0f else 360f
}
.graphicsLayer {
    // Aplica uma transformação de matriz para espelhar horizontalmente a imagem
    scaleX = animatedMirrored
    rotationZ = animatedRotationAngle
},
)

--1-------------------------------------------------------------------------------------------------

*/
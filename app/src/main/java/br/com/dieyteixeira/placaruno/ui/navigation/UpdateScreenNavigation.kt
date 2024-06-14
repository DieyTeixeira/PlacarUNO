package br.com.dieyteixeira.placaruno.ui.navigation

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.dieyteixeira.placaruno.R
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import firebase.com.protolitewrapper.BuildConfig

const val updateScreenRoute = "updatescreen"

@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.updateScreen() {
    composable(updateScreenRoute) {
        UpdateScreen()
    }
}

private val db = Firebase.firestore

@Composable
fun UpdateScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        (context as? android.app.Activity)?.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.LightGray, Color(0xFFFFFFFF)),
                    startY = 0f,
                    endY = 1000f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            val latestVersionState = remember { mutableStateOf("Carregando...") }
            db.collection("appConfig").document("version")
                .get()
                .addOnSuccessListener { document ->
                    val latestVersionName = document.getString("latestVersionName")
                    val latestVersion = latestVersionName ?: "N/A"

                    latestVersionState.value = latestVersion
                }
            Text(
                text = "Atualização disponível",
                style = TextStyle.Default.copy(
                    fontSize = 25.sp,
                    color = Color.DarkGray
                )
            )
            Text(
                text = "Clique no botão para atualizar",
                style = TextStyle.Default.copy(
                    fontSize = 25.sp,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "VERSÃO INSTALADA:",
                style = TextStyle.Default.copy(
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = formatVersionNumber(BuildConfig.VERSION_NAME),
                style = TextStyle.Default.copy(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "ATUALIZAÇÃO DISPONÍVEL:",
                style = TextStyle.Default.copy(
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = latestVersionState.value,
                style = TextStyle.Default.copy(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp) // Ajustar altura do botão
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.teste.com.br"))
                        context.startActivity(intent)
                    }),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_update),
                        contentDescription = "Atualizar",
                        tint = Color.White, // Cor do ícone
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "ATUALIZAR",
                        color = Color.White, // Cor do texto
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // vídeo mp4
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        val player = SimpleExoPlayer.Builder(ctx).build()
                        player.setMediaItem(
                            com.google.android.exoplayer2.MediaItem.fromUri(
                                "android.resource://${ctx.packageName}/${R.raw.gif_dog}"
                            )
                        )
                        player.prepare()
                        player.playWhenReady = true
                        player.repeatMode = Player.REPEAT_MODE_ONE
                        this.player = player
                        this.useController = false

                        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_PAUSE) {
                                player.pause()
                            } else if (event == Lifecycle.Event.ON_RESUME) {
                                player.play()
                            }
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White)
            )
        }
    }
}

fun NavHostController.navigateToUpdateScreen() {
    navigate(updateScreenRoute)
}

private fun formatVersionNumber(versionNumber: String?): String {
    if (versionNumber.isNullOrBlank()) return "0.0.0"

    val parts = versionNumber.split(".").toMutableList()

    while (parts.size < 3) {
        parts.add("0")
    }

    return "${parts[0]}.${parts[1]}.${parts[2]}"
}
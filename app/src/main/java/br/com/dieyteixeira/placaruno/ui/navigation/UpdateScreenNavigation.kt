package br.com.dieyteixeira.placaruno.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import br.com.dieyteixeira.placaruno.InstallUpdateApk
import br.com.dieyteixeira.placaruno.R
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import br.com.dieyteixeira.placaruno.firebase.currentVersionName
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val updateScreenRoute = "updatescreen"

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
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        (context as? android.app.Activity)?.finish()
    }

    val player = remember { SimpleExoPlayer.Builder(context).build() }
    val loopVideoUri = "android.resource://${context.packageName}/${R.raw.gif_dog}"
    val shortVideo1Uri = "android.resource://${context.packageName}/${R.raw.gif_dog_1}"
    val shortVideo2Uri = "android.resource://${context.packageName}/${R.raw.gif_dog_2}"
    val shortVideo3Uri = "android.resource://${context.packageName}/${R.raw.gif_dog_3}"

    fun setupPlayer(uri: String) {
        player.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    LaunchedEffect(Unit) {
        setupPlayer(loopVideoUri)
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
                text = currentVersionName,
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

                        coroutineScope.launch {
                            delay(1000) // Atraso de 1 segundo

                            player.clearMediaItems()

                            val concatenatedSource = ConcatenatingMediaSource().apply {
                                addMediaSource(ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, "exoplayer"))
                                    .createMediaSource(MediaItem.fromUri(shortVideo1Uri)))
                                addMediaSource(ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, "exoplayer"))
                                    .createMediaSource(MediaItem.fromUri(shortVideo2Uri)))
                                addMediaSource(ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, "exoplayer"))
                                    .createMediaSource(MediaItem.fromUri(shortVideo3Uri)))
                                addMediaSource(ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, "exoplayer"))
                                    .createMediaSource(MediaItem.fromUri(loopVideoUri)))
                            }

                            player.repeatMode = Player.REPEAT_MODE_OFF
                            player.setMediaSource(concatenatedSource)
                            player.prepare()
                            player.playWhenReady = true
                            player.addListener(object : Player.Listener {
                                override fun onMediaItemTransition(
                                    mediaItem: MediaItem?,
                                    reason: Int
                                ) {
                                    if (mediaItem?.localConfiguration?.uri.toString() == loopVideoUri) {
                                        player.repeatMode = Player.REPEAT_MODE_ONE
                                    } else {
                                        player.repeatMode = Player.REPEAT_MODE_OFF
                                    }
                                }
                            })

                            InstallUpdateApk.downloadAndInstallApk(
                                context,
                                "https://dl.dropboxusercontent.com/scl/fi/vxx84sxyzd6ivbry31zqm/Placar-UNO.apk?rlkey=sin2s0ty73988iq5yt5215g39"
                            )
                        }
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
                    .height(250.dp)
                    .background(Color.White)
            )
        }
    }
}

fun NavHostController.navigateToUpdateScreen() {
    navigate(updateScreenRoute)
}
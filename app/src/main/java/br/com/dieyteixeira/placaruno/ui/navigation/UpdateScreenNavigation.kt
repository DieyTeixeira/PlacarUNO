package br.com.dieyteixeira.placaruno.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val updateScreenRoute = "updatescreen"

fun NavGraphBuilder.updateScreen() {
    composable(updateScreenRoute) {
        UpdateScreen()
    }
}

@Composable
fun UpdateScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF000000)),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.teste.com.br"))
            context.startActivity(intent)
        }) {
            Text(text = "Atualizar")
        }
    }
}

fun NavHostController.navigateToUpdateScreen() {
    navigate(updateScreenRoute)
}
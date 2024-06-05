package br.com.dieyteixeira.placaruno.ui.compscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Definição de componente - ** BARRA DOS BOTÕES **
@Composable
fun BarraBottons(){
    Column {
        Spacer(modifier = Modifier.height(630.dp))
        HorizontalDivider(
            thickness = 1.5.dp,
            color = Color.LightGray
        ) // Linha branca com espessura de 2.dp
    }
}

// Visualização
@Preview
@Composable
fun BarraBottonsPreview(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(color = Color(0xFF000000))
    ) {
        BarraBottons()
    }
}
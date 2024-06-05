package br.com.dieyteixeira.placaruno.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme

/***** FUNÇÃO PRINCIPAL *****/
@Composable
fun DTScreen(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_dt),
            contentDescription = "Logo DT",
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.size(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Diey Teixeira\nDesenvolvimento de softwares" +
                        "\n\nAplicativo Placar UNO",
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 15.sp
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.size(50.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialMediaItem(
                iconId = R.drawable.ic_rotation,
                text = "Instagram"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SocialMediaItem(
                iconId = R.drawable.ic_rotation,
                text = "Whatsapp"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SocialMediaItem(
                iconId = R.drawable.ic_rotation,
                text = "GitHub"
            )
        }
    }
}

@Composable
fun SocialMediaItem(iconId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 15.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, name = "Padrão")
@Composable
fun DTScreenPreview() {
    PlacarUNOTheme {
        DTScreen()
    }
}
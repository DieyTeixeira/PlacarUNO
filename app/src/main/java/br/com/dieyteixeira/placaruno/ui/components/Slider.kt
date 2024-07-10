package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MaterialRangeSlider() {
    var sliderValue by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Value: ${sliderValue.toInt()}")

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = sliderValue,
                onValueChange = { newValue ->
                    sliderValue = newValue
                },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Blue,
                    activeTrackColor = Color.Blue,
                    inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
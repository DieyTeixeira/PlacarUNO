package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.dieyteixeira.placaruno.ui.theme.AmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.SecAmareloUno
import br.com.dieyteixeira.placaruno.ui.theme.VermelhoUno

@Composable
fun SwitchButton(
    initialState: Boolean,
    checkedThumbColor: Color,
    uncheckedThumbColor: Color,
    checkedTrackColor: Color,
    uncheckedTrackColor: Color,
    checkedBorderColor: Color,
    uncheckedBorderColor: Color,
) {
    var switchState by remember { mutableStateOf(initialState) }

    Spacer(modifier = Modifier.height(5.dp))
    Switch(
        checked = switchState,
        onCheckedChange = { switchState = it },
        colors = SwitchDefaults.colors(
            checkedThumbColor = checkedThumbColor,
            uncheckedThumbColor = uncheckedThumbColor,
            checkedTrackColor = checkedTrackColor,
            uncheckedTrackColor = uncheckedTrackColor,
            checkedBorderColor = checkedBorderColor,
            uncheckedBorderColor = uncheckedBorderColor
        ),
        thumbContent = {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (switchState) checkedThumbColor else uncheckedThumbColor,
                        shape = CircleShape
                    )
            )
        }
    )
}

@Composable
fun SwitchButtonNG(
    switchState: Boolean,
    checkedThumbColor: Color,
    uncheckedThumbColor: Color,
    checkedTrackColor: Color,
    uncheckedTrackColor: Color,
    checkedBorderColor: Color,
    uncheckedBorderColor: Color,
    onSwitchChange: (Boolean) -> Unit
) {

    Spacer(modifier = Modifier.height(0.3.dp))
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(65.dp)
                    .background(
                        color = if (switchState) Color.Gray else VermelhoUno,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            bottomStart = 8.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Não",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box{
                Switch(
                    checked = switchState,
                    onCheckedChange = { state ->
                        onSwitchChange(state)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = checkedThumbColor,
                        uncheckedThumbColor = uncheckedThumbColor,
                        checkedTrackColor = checkedTrackColor,
                        uncheckedTrackColor = uncheckedTrackColor,
                        checkedBorderColor = checkedBorderColor,
                        uncheckedBorderColor = uncheckedBorderColor
                    ),
                    thumbContent = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = if (switchState) checkedThumbColor else uncheckedThumbColor,
                                    shape = CircleShape
                                )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(65.dp)
                    .background(
                        color = if (switchState) VermelhoUno else Color.Gray,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            bottomStart = 8.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Sim",
                    style = TextStyle.Default.copy(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                )
            }
        }
    }
}
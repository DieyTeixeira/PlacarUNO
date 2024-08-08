package br.com.dieyteixeira.placaruno.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R

// Definição de componente - ** BOTÕES **
@Composable
fun GenericButtonBar(
    buttons: List<ButtonInfo?>,
    backgroundColor: Color = Color.Gray.copy(alpha = 0.3f)
) {
    val clickHandler = remember { ClickHandler() }

    require(buttons.size == 5) { "The buttons list must have exactly 5 elements" }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            modifier = Modifier
                .height(55.dp)
                .background(color = backgroundColor)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                buttons.forEachIndexed { _, buttonInfo ->
                    if (buttonInfo != null) {
                        GenericButton(
                            icon = buttonInfo.icon,
                            description = buttonInfo.description,
                            onClick = buttonInfo.onClick,
                            rotate = buttonInfo.rotate
                        )
                    } else {
                        Spacer(modifier = Modifier.size(40.dp)) // Espaçamento onde não há botão
                    }
                }
            }
        }
    }
}

data class ButtonInfo(
    val icon: Painter,
    val description: String,
    val onClick: () -> Unit,
    val rotate: Boolean = false
)

@Composable
fun GenericButton(
    icon: Painter,
    description: String,
    onClick: () -> Unit,
    rotate: Boolean = false,
    size: Int = 40,
    backgroundColor: Color = Color.Gray.copy(alpha = 0f),
    iconTint: Color = Color.LightGray
) {
    val clickHandler = remember { ClickHandler() }
    val rotation by animateFloatAsState(
        targetValue = if (rotate) 45f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Image(
        painter = icon,
        contentDescription = description,
        modifier = Modifier
            .size(size.dp)
            .background(backgroundColor, CircleShape)
            .clip(CircleShape)
            .clickable {
                if (clickHandler.canClick()) {
                    onClick()
                }
            }
            .rotate(rotation)
            .padding(8.dp),
        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconTint)
    )
}

//@Preview
//@Composable
//fun PreviewGenericButtonBar() {
//    GenericButtonBar(
//        buttons = listOf(
//            null, // Posição 1 sem botão
//            null, // Posição 2 sem botão
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_add),
//                description = "Add",
//                onClick = { println("Add clicked") }
//            ), // Posição 3 botão Add
//            null, // Posição 4 sem botão
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_double_arrow_left),
//                description = "Back",
//                onClick = { println("Back clicked") }
//            )  // Posição 5 botão DoubleArrow
//        ),
//        backgroundColor = Color.Black.copy(alpha = 0.5f)
//    )
//}
//
//@Preview
//@Composable
//fun PreviewGenericButtonBarAllButtons() {
//    GenericButtonBar(
//        buttons = listOf(
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_add),
//                description = "Add",
//                onClick = { println("Add clicked") }
//            ),
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_add),
//                description = "Check",
//                onClick = { println("Check clicked") }
//            ),
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_out),
//                description = "Close",
//                onClick = { println("Close clicked") }
//            ),
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_double_arrow_left),
//                description = "Back",
//                onClick = { println("Back clicked") }
//            ),
//            ButtonInfo(
//                icon = painterResource(id = R.drawable.ic_delete),
//                description = "Delete",
//                onClick = { println("Delete clicked") }
//            )
//        ),
//        backgroundColor = Color.Black.copy(alpha = 0.5f)
//    )
//}
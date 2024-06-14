package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.placaruno.R
import br.com.dieyteixeira.placaruno.ui.theme.PlacarUNOTheme


@Composable
fun FabWithSubButtons(
    onSubButton1Click: () -> Unit,
    onSubButton2Click: () -> Unit,
    onSubButton3Click: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val transition = updateTransition(expanded)

    val translation by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                keyframes {
                    durationMillis = 1500
                    20f at 0
                    10f at 150
                    5f at 300
                    0f at 500
                }
            } else {
                keyframes {
                    durationMillis = 500
                    0f at 0
                    5f at 150
                    10f at 300
                    20f at 500
                }
            }
        }
    ) { state ->
        if (state) 0f else 20f
    }

    Box(
        modifier = Modifier
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    bottomStart = 5.dp,
                    topEnd = 5.dp,
                    bottomEnd = 15.dp
                )
            ) {
                Icon(
                    painter = if (expanded) painterResource(id = R.drawable.ic_xis) else painterResource(id = R.drawable.ic_list),
                    contentDescription = null,
                    modifier = if (expanded) Modifier.size(24.dp) else Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                modifier = Modifier.offset(x = translation.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AnimatedVisibility(visible = expanded) {
                    SubFabButton(icon = painterResource(id = R.drawable.ic_layout_column), onClick = onSubButton1Click)
                }

                AnimatedVisibility(visible = expanded) {
                    SubFabButton(icon = painterResource(id = R.drawable.ic_layout_row), onClick = onSubButton2Click)
                }

                AnimatedVisibility(visible = expanded) {
                    SubFabButton(icon = painterResource(id = R.drawable.ic_layout_2x2), onClick = onSubButton3Click)
                }
            }
        }
    }
}

@Composable
fun SubFabButton(
    icon: Painter,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = RoundedCornerShape(
            topStart = 15.dp,
            bottomStart = 5.dp,
            topEnd = 5.dp,
            bottomEnd = 15.dp
        ),
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlacarUNOTheme {
        FabWithSubButtons(
            onSubButton1Click = {},
            onSubButton2Click = {},
            onSubButton3Click = {}
        )
    }
}
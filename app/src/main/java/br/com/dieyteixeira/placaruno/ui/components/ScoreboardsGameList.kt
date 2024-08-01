package br.com.dieyteixeira.placaruno.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.placaruno.ui.states.ScoreboardListUiState
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardListViewModel

@Composable
fun ScoreboardsGameList (
    playersTotalCount: Int,
    uiState: ScoreboardListUiState,
    viewModel: ScoreboardListViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.loadGames()
    }

    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(uiState.games) { index, game ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                    .heightIn(max = 200.dp)
            ) {
                items(game.game_players) { playerName ->
                    Text(
                        text = playerName,
                        style = TextStyle.Default.copy(
                            fontSize = 16.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(
                            top = 2.dp,
                            bottom = 2.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                            .heightIn(max = 200.dp)
                    ) {
                        items(game.game_players) { playerName ->
                            Text(
                                text = playerName,
                                style = TextStyle.Default.copy(
                                    fontSize = 16.sp,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(
                                    top = 2.dp,
                                    bottom = 2.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
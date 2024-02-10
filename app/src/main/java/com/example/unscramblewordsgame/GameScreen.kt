package com.example.unscramblewordsgame

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramblewordsgame.ui.theme.GameViewModel
import com.example.unscramblewordsgame.ui.theme.UnscrambleWordsGameTheme



@Composable
fun Gamescreen(
    gameViewModel: GameViewModel = viewModel()
){
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = mediumPadding)
        )
        GameLayout(
            onUserGuessChanged = {gameViewModel.updateUserGuess()},
            userGuess = gameViewModel.userGuess,
            currentScrambleWord = gameUiState.currentScrambleWord,
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp)
            }
            GameStatus(score = 0, modifier = Modifier.padding(20.dp))
        }
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout(
    onUserGuessChanged : () -> Unit,
    userGuess : String,
    currentScrambleWord : String,
    modifier : Modifier = Modifier) {
val mediumPadding = dimensionResource(R.dimen.padding_medium)
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.word_count,0),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary)

            Text(
                text = currentScrambleWord,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
                )
            OutlinedTextField(
                value = userGuess,
                onValueChange = {onUserGuessChanged() },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface),
                label = {
                    Text(text = stringResource(R.string.enter_your_word))
                        },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )
        }
    }
}

//Composable function to display the dialog box
@Composable
private fun FinalScoreDialog(
    onPlayAgain : () -> Unit,
    modifier: Modifier = Modifier
){
    val activity = (LocalContext.current as Activity)
    AlertDialog(
        title = {Text(text = stringResource(R.string.congratulations))},
        text = { Text(text = stringResource(R.string.instructions))},
        onDismissRequest = {
            //If the user touch anyone on the screen
            //it will dismiss the dialog box
        },
        confirmButton = {
            TextButton(
                onClick = {onPlayAgain()}) {
                Text(text = stringResource(R.string.play_again))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {activity.finish()}) {
                Text(text = stringResource(R.string.exit))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleWordsGameTheme {
        Gamescreen()
    }
}

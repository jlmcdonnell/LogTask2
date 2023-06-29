package dev.mcd.logtask2.ui.log

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import dev.mcd.logtask2.R

@Composable
fun LogInput(
    modifier: Modifier = Modifier,
    onLogInput: (String) -> Unit,
) {
    var textFieldValues by remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(4f),
            value = textFieldValues,
            onValueChange = { textFieldValues = it },
            label = { Text(stringResource(id = R.string.log_hint)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions {
                onLogInput(textFieldValues.text)
                textFieldValues = TextFieldValue()
            }
        )
        TextButton(
            modifier = Modifier.weight(1f),
            onClick = {
                onLogInput(textFieldValues.text)
                textFieldValues = TextFieldValue()
            }
        ) {
            Text(text = stringResource(id = R.string.log_ok))
        }
    }
}
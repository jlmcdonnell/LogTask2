package dev.mcd.logtask2.ui.log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LogScreen(viewModel: LogViewModel = hiltViewModel()) {

    val logItems by viewModel.logData.observeAsState()

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LogInput(
                modifier = Modifier.padding(16.dp),
                onLogInput = { logText ->
                    viewModel.onLogAdded(logText)
                }
            )
            LogList(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = logItems ?: emptyList()
            )
        }
    }
}

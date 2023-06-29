package dev.mcd.logtask2.ui.log

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mcd.logtask2.data.LogItem

@Composable
fun LogList(
    modifier: Modifier = Modifier,
    items: List<LogItem>,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items) { item ->
            LogItemView(item)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LogItemView(item: LogItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "[${item.timestamp}]")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.text)
    }
}
package dev.mcd.logtask2.data

data class LogItem(
    val timestamp: String,
    val text: String,
)

data class LogItemSerializer(
    val items: List<LogItem>
)
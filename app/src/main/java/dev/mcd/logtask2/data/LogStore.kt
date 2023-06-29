package dev.mcd.logtask2.data

import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class LogStore @Inject constructor(private val logFile: File) {

    private val gson = Gson()
    private lateinit var cachedLogData: MutableStateFlow<List<LogItem>>

    fun data() = flow {
        ensureInitialized()
        emitAll(cachedLogData)
    }

    suspend fun addItem(logItem: LogItem) {
        ensureInitialized()

        val items = cachedLogData.value.toMutableList() + logItem
        cachedLogData.emit(items)
        saveData(items)
    }

    private fun loadData(): List<LogItem> {
        val cachedItems = gson.fromJson(logFile.reader(), LogItemSerializer::class.java)?.items
        return cachedItems ?: emptyList()
    }

    private fun saveData(items: List<LogItem>) {
        logFile.writeText(gson.toJson(LogItemSerializer(items)))
    }

    /**
     * Ensures cachedLogData is initialized, this is called in the public facing methods so as
     * not to do any IO work when the LogStore is initialized itself which will be on the main thread
     */
    private fun ensureInitialized() {
        if (!::cachedLogData.isInitialized) {
            cachedLogData = MutableStateFlow(loadData())
        }
    }
}
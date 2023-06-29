package dev.mcd.logtask2.ui.log

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.mcd.logtask2.data.LogItem
import dev.mcd.logtask2.data.LogStore
import dev.mcd.logtask2.data.localTimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.time.LocalTime

class LogViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cacheFile: File
    private lateinit var store: LogStore

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cacheFile = File.createTempFile("log", ".json")
        store = LogStore(cacheFile)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        cacheFile.delete()
    }

    @Test
    fun `emit log items`() = runBlocking {
        val item = LogItem(timestamp = "time", text = "text")
        store.addItem(item)

        val viewModel = LogViewModel(store)

        assertEquals(listOf(item), viewModel.logData.value)
    }

    @Test
    fun `add log item`() = runBlocking {
        val time = LocalTime.now().format(localTimeFormatter)
        val item = LogItem(timestamp = time, text = "text")
        val viewModel = LogViewModel(store)

        assertEquals(emptyList<LogItem>(), viewModel.logData.value)
        viewModel.onLogAdded("text")
        assertEquals(listOf(item), viewModel.logData.value)
    }
}

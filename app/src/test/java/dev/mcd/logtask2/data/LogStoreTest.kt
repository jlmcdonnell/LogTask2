package dev.mcd.logtask2.data

import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class LogStoreTest {

    private lateinit var cacheFile: File

    @Before
    fun setUp() {
        cacheFile = File.createTempFile("log", ".json")
    }

    @After
    fun tearDown() {
        cacheFile.delete()
    }

    @Test
    fun `emit empty list if cache is empty`() = runBlocking {
        val store = LogStore(cacheFile)
        store.data().test {
            assertEquals(emptyList<LogItem>(), awaitItem())
        }
    }

    @Test
    fun `emit cached logs`() = runBlocking {
        val store = LogStore(cacheFile)
        val item = LogItem(timestamp = "time", text = "text")

        store.addItem(item)

        // The second log store simulates the process being killed and restarted, it should
        // reinitialize from the cache
        val store2 = LogStore(cacheFile)
        store2.data().test {
            assertEquals(listOf(item), awaitItem())
        }
    }

    @Test
    fun `emit log when updated`() = runBlocking {
        val store = LogStore(cacheFile)
        val item = LogItem(timestamp = "time", text = "text")
        store.data().test {
            assertEquals(emptyList<LogItem>(), awaitItem())
            store.addItem(item)
            assertEquals(listOf(item), awaitItem())
        }
    }
}

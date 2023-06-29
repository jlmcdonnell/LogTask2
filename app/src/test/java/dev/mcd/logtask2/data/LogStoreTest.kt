package dev.mcd.logtask2.data

import app.cash.turbine.test
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.File

class LogStoreTest : FunSpec({

    isolationMode = IsolationMode.InstancePerLeaf
    val cacheFile = File.createTempFile("log", ".json")

    afterTest {
        cacheFile.delete()
    }

    test("emit empty list if cache is empty") {
        val store = LogStore(cacheFile)
        store.data().test {
            awaitItem() shouldBe emptyList()
        }
    }

    test("emit cached logs") {
        val store = LogStore(cacheFile)
        val item = LogItem(timestamp = "time", text = "text")

        store.addItem(item)

        // The second log store simulates the process being killed and restarted, it should
        // reinitialize from the cache
        val store2 = LogStore(cacheFile)
        store2.data().test {
            awaitItem() shouldBe listOf(item)
        }
    }

    test("emit log when updated") {
        val store = LogStore(cacheFile)
        val item = LogItem(timestamp = "time", text = "text")
        store.data().test {
            awaitItem() shouldBe emptyList()
            store.addItem(item)
            awaitItem() shouldBe listOf(item)
        }
    }
})

package dev.mcd.logtask2.ui.log

import dev.mcd.logtask2.data.LogItem
import dev.mcd.logtask2.data.LogStore
import dev.mcd.logtask2.data.localTimeFormatter
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import java.io.File
import java.time.LocalTime

class LogViewModelTest : FunSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    Dispatchers.setMain(Dispatchers.Unconfined)

    val cacheFile = File.createTempFile("log", ".json")
    val store = LogStore(cacheFile)

    afterTest {
        cacheFile.delete()
    }

    test("emit log items") {
        val item = LogItem(timestamp = "time", text = "text")
        store.addItem(item)

        val viewModel = LogViewModel(store)

        viewModel.logData.value shouldBe listOf(item)
    }

    test("add log item") {
        val time = LocalTime.now().format(localTimeFormatter)
        val item = LogItem(timestamp = time, text = "text")
        val viewModel = LogViewModel(store)

        viewModel.logData.value shouldBe emptyList()
        viewModel.onLogAdded("text")
        viewModel.logData.value shouldBe listOf(item)
    }

})

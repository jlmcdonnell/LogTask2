@file:Suppress("JUnitMalformedDeclaration")

package dev.mcd.logtask2.ui.log

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import dev.mcd.logtask2.R
import dev.mcd.logtask2.data.LogStore
import dev.mcd.logtask2.data.localTimeFormatter
import dev.mcd.logtask2.ui.LogTask2Theme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.time.LocalTime

class LogScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var cacheFile: File
    private lateinit var viewModel: LogViewModel
    private val context: Context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun addLog_clearsInput() {
        setupTestContent()
        with(composeRule) {
            onNodeWithTag("log-input")
                .performTextInput("Hello, World!")

            onNodeWithText("OK")
                .performClick()

            onNodeWithText(context.getString(R.string.log_hint))
                .assertExists()
        }
    }

    @Test
    fun addLog_appendsList_timeIsShown() {
        setupTestContent()
        val time = LocalTime.now().format(localTimeFormatter)

        with(composeRule) {
            onNodeWithTag("log-input")
                .performTextInput("Hello, World!")

            onNodeWithText("OK")
                .performClick()

            onNodeWithText("[$time]")
                .assertExists()
        }
    }

    @Test
    fun addLog_appendsList_textIsShown() {
        setupTestContent()
        with(composeRule) {
            onNodeWithTag("log-input")
                .performTextInput("Hello, World!")

            onNodeWithText("OK")
                .performClick()

            onNodeWithText("Hello, World!")
                .assertExists()
        }
    }

    @Before
    fun setUp() {
        cacheFile = TemporaryFolder().let {
            it.create()
            it.newFile()
        }
        viewModel = LogViewModel(LogStore(cacheFile))
    }

    @After
    fun tearDown() {
        cacheFile.delete()
    }

    private fun setupTestContent() {
        composeRule.setContent {
            LogTask2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LogScreen(viewModel)
                }
            }
        }
    }
}
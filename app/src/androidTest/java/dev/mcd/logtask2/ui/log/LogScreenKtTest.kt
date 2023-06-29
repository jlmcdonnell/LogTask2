@file:Suppress("JUnitMalformedDeclaration")

package dev.mcd.logtask2.ui.log

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import de.mannodermaus.junit5.compose.ComposeContext
import de.mannodermaus.junit5.compose.createComposeExtension
import dev.mcd.logtask2.R
import dev.mcd.logtask2.data.LogStore
import dev.mcd.logtask2.data.localTimeFormatter
import dev.mcd.logtask2.ui.LogTask2Theme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.rules.TemporaryFolder
import java.io.File
import java.time.LocalTime

class LogScreenKtTest {

    @JvmField
    @RegisterExtension
    val composeExtension = createComposeExtension()

    private lateinit var cacheFile: File
    private lateinit var viewModel: LogViewModel
    private val context: Context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun addLog_clearsInput() {
        logScreenTest {
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
        logScreenTest {
            val time = LocalTime.now().format(localTimeFormatter)

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
        logScreenTest {
            onNodeWithTag("log-input")
                .performTextInput("Hello, World!")

            onNodeWithText("OK")
                .performClick()

            onNodeWithText("Hello, World!")
                .assertExists()
        }
    }

    @BeforeEach
    fun setUp() {
        cacheFile = TemporaryFolder().let {
            it.create()
            it.newFile()
        }
        viewModel = LogViewModel(LogStore(cacheFile))
    }

    @AfterEach
    fun tearDown() {
        cacheFile.delete()
    }

    private fun logScreenTest(block: ComposeContext.() -> Unit) {
        composeExtension.use {
            setContent {
                LogTask2Theme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        LogScreen(viewModel)
                    }
                }
            }
            block(this)
        }
    }
}
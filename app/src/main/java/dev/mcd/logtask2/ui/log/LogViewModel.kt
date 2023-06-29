package dev.mcd.logtask2.ui.log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.logtask2.data.LogItem
import dev.mcd.logtask2.data.LogStore
import dev.mcd.logtask2.data.localTimeFormatter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(private val logStore: LogStore) : ViewModel() {

    val logData = MutableLiveData<List<LogItem>>(emptyList())

    init {
        viewModelScope.launch {
            logStore.data().collectLatest { log ->
                logData.postValue(log)
            }
        }
    }

    fun onLogAdded(text: String) {
        viewModelScope.launch {
            val item = LogItem(
                timestamp = LocalTime.now().format(localTimeFormatter),
                text = text,
            )
            logStore.addItem(item)
        }
    }
}
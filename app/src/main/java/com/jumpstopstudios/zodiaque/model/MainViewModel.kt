package com.jumpstopstudios.zodiaque.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jumpstopstudios.zodiaque.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainViewModel : ViewModel() {

    private val _horoscope = MutableLiveData<String>()
    val horoscope: LiveData<String> = _horoscope

    init {
        _horoscope.value = "Error!"
    }

    fun generateHoroscope(sign: String) {
        _horoscope.value = "Loading..."
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    val sourceUrl = "https://www.dailyhoroscope.com/horoscopes/daily/$sign?full=true"
                    val document = Jsoup.connect(sourceUrl).get()
                    val text = document.select(".body:first-of-type").textNodes()[0].text()
                    _horoscope.postValue(text)
                } catch(e: Throwable){
                    _horoscope.postValue("Your horoscope is not available at this time. :)")
                    Log.d(TAG, e.stackTraceToString())
                }
            }
        }
    }
}
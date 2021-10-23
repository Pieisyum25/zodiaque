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

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _horoscope = MutableLiveData<List<Site>>()
    val horoscope: LiveData<List<Site>> = _horoscope

    private val sectionsTotal: Int;

    init {
        _status.value = "Error!"

        val sites = mutableListOf<Site>()
        sites.add(
            Site("Daily Horoscope", "A new horoscope everyday!",
                mutableListOf(
                    Section(
                        "Your daily horoscope:",
                        "https://www.dailyhoroscope.com/horoscopes/daily/%s?full=true",
                        { document -> document.select(".body:first-of-type").textNodes()[0].text() }
                    )
                )
            )
        )

        sectionsTotal = sites.flatMap { it.sections }.count()
        _horoscope.value = sites
    }

    fun generateHoroscope(sign: String) {
        _status.value = "Loading..."
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                var sectionsLoaded = 0

                // Attempt to scrape content from each site:
                for (site in horoscope.value!!){
                    for (section in site.sections){
                        try {
                            section.apply {
                                content = scrapeFunction(Jsoup.connect(url.format(sign)).get())
                            }
                            sectionsLoaded++
                        } catch(e: Throwable){
                            Log.d(TAG,
                                "Section: ${section.title} of Site: ${site.name} " +
                                        "failed to load:\n" + e.stackTraceToString())
                        }
                    }
                }

                // Report status:
                if (sectionsLoaded > 0) _status.postValue("Loading Finished. (Sections: $sectionsLoaded/$sectionsTotal)")
                else _status.postValue("Your horoscope is currently unavailable. :)")
            }
        }
    }
}
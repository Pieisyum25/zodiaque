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

    val horoscope: List<Site>

    private val sectionsTotal: Int

    init {
        _status.value = "Error!"

        val sites = mutableListOf<Site>()
        sites.add(
            Site("Daily Horoscope", "A new horoscope everyday!",
                mutableListOf(
                    Section(
                        "Today's Horoscope:",
                        "https://www.dailyhoroscope.com/horoscopes/daily/%s?full=true",
                        { document -> document.select(".body:first-of-type").textNodes()[0].text() }
                    ),
                    Section(
                        "Tomorrow's Horoscope:",
                        "https://www.dailyhoroscope.com/horoscopes/daily/%s?tomorrow&full=true",
                        { document -> document.select(".body:first-of-type").textNodes()[0].text() }
                    )
                )
            )
        )

        sectionsTotal = sites.flatMap { it.sections }.count()
        horoscope = sites
    }

    fun generateHoroscope(sign: String) {
        _status.value = "Loading Horoscope... (Sections: 0/$sectionsTotal)"

        // Set each section's content to loading message:
        horoscope.flatMap { it.sections }.map { it.content }.forEach { it.value = "Loading..." }

        // Launch coroutine to web-scrape horoscope:
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                var sectionsLoaded = 0

                // Attempt to scrape content from each site:
                for (site in horoscope){
                    for (section in site.sections){
                        try {
                            section.apply {
                                content.postValue(scrapeFunction(Jsoup.connect(url.format(sign)).get()))
                            }
                        } catch(e: Throwable){
                            section.content.postValue("Loading failed.")
                            Log.d(TAG,
                                "Section: ${section.title} of Site: ${site.name} " +
                                        "failed to load:\n" + e.stackTraceToString())
                        }
                        sectionsLoaded++
                        _status.postValue("Loading Horoscope... (Sections: $sectionsLoaded/$sectionsTotal)")
                    }
                }

                // Force UI change:
                //_horoscope.postValue(horoscope.value)

                // Report status:
                if (sectionsLoaded > 0) _status.postValue("Loading Finished. (Sections: $sectionsLoaded/$sectionsTotal)")
                else _status.postValue("Your horoscope is currently unavailable. :)")
            }
        }
    }
}
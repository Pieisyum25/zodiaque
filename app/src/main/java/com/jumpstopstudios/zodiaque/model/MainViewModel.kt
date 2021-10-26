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

    val horoscope: List<Site>

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> = _status
    private val _sectionsLoaded = MutableLiveData<Int>()
    val sectionsLoaded: LiveData<Int> = _sectionsLoaded
    val sectionsTotal: Int


    init {
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
        _status.value = LoadingStatus.LOADING
        _sectionsLoaded.value = 0
        var sectionsLoadedSuccessfully = 0

        // Set each section's content to loading message:
        horoscope.flatMap { it.sections }.map { it.content }.forEach { it.value = "Loading..." }

        // Launch coroutine to web-scrape horoscope:
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                // Attempt to scrape content for each section:
                for (site in horoscope){
                    for (section in site.sections){
                        try {
                            section.apply {
                                content.postValue(scrapeFunction(Jsoup.connect(url.format(sign)).get()))
                            }
                            sectionsLoadedSuccessfully++
                        } catch(e: Throwable){
                            section.content.postValue("Loading failed.")
                            Log.d(TAG,
                                "Section: ${section.title} of Site: ${site.name} " +
                                        "failed to load:\n" + e.stackTraceToString())
                        }
                        _sectionsLoaded.postValue(sectionsLoaded.value?.plus(1))
                    }
                }

                // Report status:
                if (sectionsLoadedSuccessfully > 0) _status.postValue(LoadingStatus.SUCCESSFUL)
                else _status.postValue(LoadingStatus.FAILED)
            }
        }
    }
}
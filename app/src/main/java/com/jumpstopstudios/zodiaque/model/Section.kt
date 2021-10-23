package com.jumpstopstudios.zodiaque.model

import org.jsoup.nodes.Document

data class Section(
    val title: String,
    val url: String,
    val scrapeFunction: (Document) -> String,
    var content: String = "Loading Error"
)

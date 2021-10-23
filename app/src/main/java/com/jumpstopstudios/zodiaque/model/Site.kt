package com.jumpstopstudios.zodiaque.model

class Site(
    val name: String,
    val description: String,
    val sections: List<Section>
){
    fun copy(): Site = Site(name, description, sections.map { it.copy() })
}

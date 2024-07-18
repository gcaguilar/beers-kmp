package org.gcaguilar.kmmbeers.domain

interface BaseBrewery {
    val id: Int
    val name: String
}

data class Brewery(
    override val id: Int,
    override val name: String,
    val description: String? = null,
    val imageUrl: String,
    val country: String,
    val beerCounts: Int,
    val location: Location
) : BaseBrewery

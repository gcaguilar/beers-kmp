package org.gcaguilar.kmmbeers.data

import org.gcaguilar.kmmbeers.domain.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    @SerialName("brewery_address") val breweryAddress: String? = null,
    @SerialName("brewery_city") val breweryCity: String,
    @SerialName("brewery_state") val breweryState: String,
    @SerialName("brewery_lat") val breweryLat: Double? = null,
    @SerialName("brewery_lng") val breweryLng: Double? = null,
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lng") val lng: Double? = null
)

fun LocationResponse.toLocation() = Location(
    city = breweryCity,
    state = breweryState,
    lat = breweryLat ?: lat!!,
    lng = breweryLat ?: lng!!
)

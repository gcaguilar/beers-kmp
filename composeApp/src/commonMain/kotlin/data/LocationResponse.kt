package data

import domain.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    @SerialName("brewery_address") val breweryAddress: String,
    @SerialName("brewery_city") val breweryCity: String,
    @SerialName("brewery_state") val breweryState: String,
    @SerialName("brewery_lat") val breweryLat: Double,
    @SerialName("brewery_lng") val breweryLng: Double
)

fun LocationResponse.toLocation() = Location(
    city = breweryCity,
    lng = breweryAddress,
    state = breweryState,
    lat = "",
)
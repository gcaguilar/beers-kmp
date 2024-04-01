package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class BreweryDetailResponse(
    @SerialName("response") val response: BreweryDetailResultResponse
)

data class BreweryDetailResultResponse(
    @SerialName("brewery") val brewery: BreweryDetailItemResponse
)

@Serializable
data class BreweryDetailItemResponse(
    @SerialName("brewery_id") val breweryId: Int,
    @SerialName("brewery_name") val breweryName: String,
    @SerialName("brewery_slug") val brewerySlug: String,
    @SerialName("brewery_label") val breweryLabel: String,
    @SerialName("country_name") val countryName: String,
    @SerialName("brewery_in_production") val breweryInProduction: Int,
    @SerialName("is_independent") val isIndependent: Int,
    @SerialName("claimed_status") val claimedStatus: ClaimedStatus,
    @SerialName("beer_count") val beerCount: Int,
    @SerialName("contact") val contact: Contact,
    @SerialName("brewery_type") val breweryType: String,
    @SerialName("brewery_type_id") val breweryTypeId: Int,
    @SerialName("location") val location: LocationResponse,
    @SerialName("rating") val rating: Rating,
    @SerialName("brewery_description") val breweryDescription: String,
    @SerialName("stats") val stats: Stats,
    @SerialName("owners") val owners: Owners
)

@Serializable
data class ClaimedStatus(
    @SerialName("is_claimed") val isClaimed: Boolean,
    @SerialName("claimed_slug") val claimedSlug: String,
    @SerialName("follow_status") val followStatus: Boolean,
    @SerialName("follower_count") val followerCount: Int,
    @SerialName("uid") val uid: Int,
    @SerialName("mute_status") val muteStatus: String
)

@Serializable
data class Rating(
    @SerialName("count") val count: Int,
    @SerialName("rating_score") val ratingScore: Double
)

@Serializable
data class Owners(
    @SerialName("count") val count: Int,
)

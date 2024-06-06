package data

import domain.BeerDetail
import domain.Brewery
import domain.BrewerySummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeerDetailResponse(
    @SerialName("response") val response: BeerDetailResultResponse
)

@Serializable
data class BeerDetailResultResponse(
    @SerialName("beer") val beer: Beer
)

@Serializable
data class Beer(
    val bid: Int,
    @SerialName("beer_name") val beerName: String,
    @SerialName("beer_label") val beerLabel: String,
    @SerialName("beer_abv") val beerAbv: Double,
    @SerialName("beer_ibu") val beerIbu: Int,
    @SerialName("beer_description") val beerDescription: String,
    @SerialName("beer_style") val beerStyle: String,
    @SerialName("beer_active") val beerActive: Int? = null,
    @SerialName("is_in_production") val isInProduction: Int,
    @SerialName("beer_slug") val beerSlug: String,
    @SerialName("is_homebrew") val isHomebrew: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("rating_count") val ratingCount: Int? = null,
    @SerialName("rating_score") val ratingScore: Double? = null,
    val stats: Stats,
    @SerialName("brewery") val brewery: BreweryResponse,
    @SerialName("auth_rating") val authRating: Int,
    @SerialName("wish_list") val wishList: Boolean,
)

@Serializable
data class Stats(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("monthly_count") val monthlyCount: Int,
    @SerialName("total_user_count") val totalUserCount: Int,
    @SerialName("user_count") val userCount: Int
)

@Serializable
data class BreweryResponse(
    @SerialName("brewery_id") val breweryId: Int,
    @SerialName("brewery_name") val breweryName: String,
    @SerialName("brewery_label") val breweryLabel: String,
    @SerialName("country_name") val countryName: String,
    val contact: Contact,
    @SerialName("location") val location: LocationResponse
)

fun BeerDetailResponse.toBeerDetail(): BeerDetail {
    return BeerDetail(
        bid = response.beer.bid.toString(),
        name = response.beer.beerName,
        description = response.beer.beerDescription,
        abv = response.beer.beerAbv.toString(),
        ibu = response.beer.beerIbu.toString(),
        style = response.beer.beerStyle,
        image = response.beer.beerLabel,
        rating = response.beer.ratingScore ?: 0.0,
        numberOfVotes = response.beer.ratingCount ?: 0,
        whisList = response.beer.wishList,
        beerActive = response.beer.beerActive == 1,
        brewery = response.beer.brewery.toBrewerySummary(),
    )
}

private fun BreweryResponse.toBrewerySummary(): BrewerySummary = BrewerySummary(
    id = breweryId,
    name = breweryName
)

fun BreweryDetailResponse.toBrewery(): Brewery = Brewery(
    id = response.brewery.breweryId,
    name = response.brewery.breweryName,
    beerCounts = response.brewery.beerCount,
    country = response.brewery.countryName,
    imageUrl = response.brewery.breweryLabel,
    location = response.brewery.location.toLocation()
)
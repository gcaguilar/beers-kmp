package data

import domain.Beer
import domain.BrewerySummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchBeerResponse(
    @SerialName("response") val response: SearchBeerResultResponse
)

@Serializable
data class Homebrew(
    @SerialName("count") val count: Int,
   @SerialName("items") val items: List<String>
)

@Serializable
@SerialName("brewery")
data class BreweryItemResponse(
    @SerialName("brewery_type") val breweryType: String,
    @SerialName("brewery_name") val breweryName: String,
    @SerialName("contact") val contact: Contact,
    @SerialName("country_name") val countryName: String,
    @SerialName("brewery_page_url") val breweryPageUrl: String,
    @SerialName("brewery_slug") val brewerySlug: String,
    @SerialName("brewery_label") val breweryLabel: String,
    @SerialName("location") val location: LocationResponse,
    @SerialName("brewery_id") val breweryId: Int,
    @SerialName("brewery_active") val breweryActive: Int
)

@Serializable
data class ItemsItem(
    @SerialName("checkin_count") val checkinCount: Int,
    @SerialName("your_count") val yourCount: Int,
    @SerialName("brewery") val brewery: BreweryItemResponse,
    @SerialName("have_had") val haveHad: Boolean,
    @SerialName("beer") val beerBeerItemResponse: BeerBeerItemResponse
)

@Serializable
data class Beers(
    @SerialName("count") val count: Int,
    @SerialName("items") val items: List<ItemsItem>
)

@Serializable
data class Breweries(
    @SerialName("count") val count: Int,
    @SerialName("items") val items: List<BreweryItemResponse>
)

@Serializable
data class Contact(
    @SerialName("twitter") val twitter: String? = null,
    @SerialName("facebook") val facebook: String? = null,
    @SerialName("instagram") val instagram: String? = null,
    @SerialName("url") val url: String? = null
)

@Serializable
data class SearchBeerResultResponse(
    @SerialName("offset") val offset: Int,
    @SerialName("type_id") val typeId: Int,
    @SerialName("breweries") val breweries: Breweries,
    @SerialName("message") val message: String,
    @SerialName("search_type") val searchType: String,
    @SerialName("time_taken") val timeTaken: Double,
    @SerialName("search_version") val searchVersion: Int,
    @SerialName("found") val found: Int,
    @SerialName("parsed_term") val parsedTerm: String,
    @SerialName("beers") val beers: Beers,
    @SerialName("limit") val limit: Int,
    @SerialName("term") val term: String,
    @SerialName("brewery_id") val breweryId: Int
)

@Serializable
@SerialName("beer")
data class BeerBeerItemResponse(
    @SerialName("beer_label") val beerLabel: String,
    @SerialName("beer_abv") val beerAbv: Double,
    @SerialName("beer_style") val beerStyle: String,
    @SerialName("beer_ibu") val beerIbu: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("wish_list") val wishList: Boolean,
    @SerialName("bid") val bid: Int,
    @SerialName("beer_slug") val beerSlug: String,
    @SerialName("in_production") val inProduction: Int,
    @SerialName("beer_description") val beerDescription: String,
    @SerialName("auth_rating") val authRating: Double,
    @SerialName("beer_name") val beerName: String
)

fun SearchBeerResponse.toBeerList(): List<Beer> {
    return response.beers.items.map {
        it.toBeer()
    }
}

private fun ItemsItem.toBeer(): Beer = Beer(
    bid = beerBeerItemResponse.bid,
    name = beerBeerItemResponse.beerName,
    abv = beerBeerItemResponse.beerAbv,
    ibu = beerBeerItemResponse.beerIbu,
    image = beerBeerItemResponse.beerLabel,
    style = beerBeerItemResponse.beerStyle,
    rate = beerBeerItemResponse.authRating,
    brewery = brewery.toBrewerySummary()
)

private fun BreweryItemResponse.toBrewerySummary(): BrewerySummary = BrewerySummary(
    id = breweryId,
    name = breweryName
)

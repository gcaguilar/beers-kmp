package domain

interface SearchRepository {
    suspend fun search(name: String): Result<List<Beer>>
    suspend fun getBeer(bid: String): Result<BeerDetail>
    suspend fun getBrewery(bid: String): Result<Brewery>
}
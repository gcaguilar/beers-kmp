package domain

interface SearchRepository {
    suspend fun search(searchName: String): Result<BeersWithPagination>
    suspend fun search(searchName: String, page: Int): Result<BeersWithPagination>
    suspend fun getBeer(bid: String): Result<BeerDetail>
    suspend fun getBrewery(id: String): Result<Brewery>
}

package domain

class GetBreweryDetail(
    private val searchRepository: SearchRepository  
) {
    suspend operator fun invoke(id: String): Result<Brewery> {
        return searchRepository.getBeweryDetail(bid)
    }
}
package domain


class GetBeerDetail(
    private val searchRepository: SearchRepository  
) {
    suspend operator fun invoke(bid: String): Result<BeerDetail> {
        return searchRepository.getBeer(bid)
    }
}
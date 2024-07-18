package org.gcaguilar.kmmbeers.domain

class GetBreweryDetail(
    private val searchRepository: SearchRepository  
) {
    suspend operator fun invoke(id: String): Result<Brewery> {
        return searchRepository.getBrewery(id)
    }
}

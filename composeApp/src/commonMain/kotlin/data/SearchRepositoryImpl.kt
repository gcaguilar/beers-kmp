package data

import domain.Beer
import domain.BeerDetail
import domain.Brewery
import domain.SearchRepository
import org.koin.core.component.KoinComponent

class SearchRepositoryImpl(
    private val service: UntappdService
) : SearchRepository, KoinComponent {
    override suspend fun search(name: String): Result<List<Beer>> {
        return service.searchBeer(name)
            .mapCatching { it.toBeerList() }
    }

    override suspend fun getBeer(bid: String): Result<BeerDetail> {
        return service.getBeerDetail(bid)
            .mapCatching { it.toBeerDetail() }
    }

    override suspend fun getBrewery(id: String): Result<Brewery> {
        return service.getBreweryDetail(id)
            .mapCatching { it.toBrewery() }
    }
}

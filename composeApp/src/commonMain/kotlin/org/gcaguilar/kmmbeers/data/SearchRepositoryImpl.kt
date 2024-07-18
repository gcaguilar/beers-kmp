package org.gcaguilar.kmmbeers.data

import org.gcaguilar.kmmbeers.domain.BeerDetail
import org.gcaguilar.kmmbeers.domain.BeersWithPagination
import org.gcaguilar.kmmbeers.domain.Brewery
import org.gcaguilar.kmmbeers.domain.SearchRepository
import org.koin.core.component.KoinComponent

class SearchRepositoryImpl(
    private val service: UntappdService
) : SearchRepository, KoinComponent {
    override suspend fun search(searchName: String): Result<BeersWithPagination> {
        return service.searchBeer(searchName)
            .mapCatching { it.toBeersWithPagination() }
    }

    override suspend fun search(searchName: String, offset: Int): Result<BeersWithPagination> {
        return service.searchBeer(searchName, offset)
            .mapCatching { it.toBeersWithPagination() }
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

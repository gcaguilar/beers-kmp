package org.gcaguilar.kmmbeers.domain

import org.koin.core.component.KoinComponent

class SearchBeer(
    private val searchRepository: SearchRepository
) : KoinComponent {
    suspend operator fun invoke(searchName: String): Result<BeersWithPagination> {
        return searchRepository.search(searchName)
    }

    suspend operator fun invoke(searchName: String, offset: Int): Result<BeersWithPagination> {
        return searchRepository.search(searchName, offset)
    }
}

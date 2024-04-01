package domain

import org.koin.core.component.KoinComponent

class SearchBeer(
    private val searchRepository: SearchRepository
): KoinComponent {
    suspend operator fun invoke(name: String): Result<List<Beer>> {
        return searchRepository.search(name)
    }
}
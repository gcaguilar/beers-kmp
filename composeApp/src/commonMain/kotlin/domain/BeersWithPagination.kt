package domain

data class BeersWithPagination(
    val nextPage: Int?,
    val beerList: List<Beer>
)

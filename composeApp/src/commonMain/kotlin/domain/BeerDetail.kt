package domain

data class BeerDetail(
    val bid: String,
    val name: String,
    val description: String,
    val abv: String,
    val ibu: String,
    val style: String,
    val image: String,
    val rating: String,
    val whisList: Boolean,
    val beerActive: Boolean,
    val brewery: BrewerySummary
)

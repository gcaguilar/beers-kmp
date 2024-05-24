package domain

data class Beer(
    val bid: Int,
    val name: String,
    val style: String,
    val abv: Double,
    val ibu: Int,
    val rate: Double,
    val brewery: BrewerySummary,
    val image: String
)

data class BrewerySummary(override val id: Int, override val name: String): BaseBrewery

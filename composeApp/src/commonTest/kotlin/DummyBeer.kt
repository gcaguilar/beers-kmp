import domain.Beer
import domain.BeersWithPagination
import domain.BrewerySummary

object DummyBrewery {
    val brewery = BrewerySummary(1, "Brewery 1")
}

object DummyBeer {
    fun generateFakeBeers(count: Int): List<Beer> {
        val beers = mutableListOf<Beer>()
        for (i in 1..count) {
            val beer = Beer(
                bid = i,
                name = "Beer $i",
                style = "Style $i",
                abv = 5.0 + i * 0.1,
                ibu = 20 + i,
                rate = 4.0 + i * 0.1,
                brewery = BrewerySummary(i, "Brewery $i"),
                image = "https://example.com/beer$i.jpg"
            )
            beers.add(beer)
        }
        return beers
    }

    val beersWithOutPagination = listOf(
        BeersWithPagination(
            nextPage = null,
            beerList = generateFakeBeers(count = 1),
        )
    )

    val beersWithPagination = listOf(
        BeersWithPagination(
            nextPage = 50,
            beerList = generateFakeBeers(count = 25),
        )
    )
}
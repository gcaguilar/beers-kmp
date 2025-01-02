package org.gcaguilar.kmmbeers.presentation

sealed class Screen(val route: String) {
    data object Splash : Screen(route = "splash")
    data object Authentitcation : Screen(route = "authentication")
    data object Search : Screen(route = "search")
    data object BeerDetail : Screen(route = "beerDetail/{id}") {
        fun createRoute(id: String) = "beerDetail/$id"
    }

    data object BreweryDetail : Screen(route = "breweryDetail/{id}") {
        fun createRoute(id: String) = "breweryDetail/$id"
    }
}
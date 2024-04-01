package presentation.brewery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import data.Location
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.koinInject

data class BreweryDetail(val id: String): Screen {
    
    @Composable
    override fun Content() {
        BreweryDetailContent(id)
    }
}

@Composable
fun BreweryDetailContent(
    id: String,
    breweryDetail: BreweryDetailViewModel = koinInject<BreweryDetailViewModel>()
) {
   Column(
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.Start,
   ) {
      
   }
}

@Composable
private fun Brewery(
    name: String,
    description: String? = null,
    imageUrl: String,
    country: String,
    beerCounts: Int,
    location: Location
) {
    KamelImage(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp),
        resource = asyncPainterResource(imageUrl),
        contentDescription = "",
        contentScale = ContentScale.Fit
    ) 
} 
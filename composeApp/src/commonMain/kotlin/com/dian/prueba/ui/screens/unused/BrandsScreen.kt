package com.dian.prueba.ui.screens.unused

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
// SIN USO
@Composable
fun BrandScreen(){
    val luxuryBrands = listOf(
        "Gucci", "Louis Vuitton", "Chanel", "Rolex", "Hermès",
        "Prada", "Dior", "Cartier", "Balenciaga", "Yves Saint Laurent",
        "Versace", "Fendi", "Bvlgari", "Tiffany & Co.", "Armani",
        "Patek Philippe", "Vacheron Constantin", "Off-White", "Givenchy",
        "Burberry", "Montblanc", "Loewe", "Bottega", "Celine", "Miu miu"
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ){
        items(luxuryBrands){ brand ->
            BrandItem(brand)
        }
    }
}
@Composable
fun BrandItem(brand: String){
    Card (
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // https://htmlcolorcodes.com/es/selector-de-color/
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        shape = MaterialTheme.shapes.medium
    ){
        Box (
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = brand,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }
    }

}
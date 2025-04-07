package com.dian.prueba.ui

import androidx.compose.material.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*

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
        elevation = 4.dp,
        // https://htmlcolorcodes.com/es/selector-de-color/
        backgroundColor = Color(0xFFffecec),
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
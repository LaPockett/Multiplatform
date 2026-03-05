package com.dian.prueba.data.product.mapper

import com.dian.prueba.data.product.model.Product
import com.dian.prueba.domain.product.model.ProductDetailUIModel

fun Product.toDetailUIModel(): ProductDetailUIModel {
    return ProductDetailUIModel(
        id = _id,
        brand = brand,
        manufacturingCountry = manufacturingCountry,
        productName = productName,
        storyTelling = storyTelling,
        styleIt = styleIt,
        type = type,
        variants = variants
    )
}
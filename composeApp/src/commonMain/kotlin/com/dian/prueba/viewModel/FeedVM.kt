package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dian.prueba.data.model.ProductUIModel
import com.dian.prueba.data.modelProduct.ProductDetailUIModel
import com.dian.prueba.repository.FeedRepository
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedVM(
    private val feedRepository: FeedRepository
): ViewModel() {
    private val _productList = MutableStateFlow<List<ProductUIModel>>(emptyList())
    val productList: StateFlow<List<ProductUIModel>> = _productList.asStateFlow()
    private val _productDetail = MutableStateFlow<ProductDetailUIModel?>(null)
    val productDetail: StateFlow<ProductDetailUIModel?> = _productDetail.asStateFlow()
    private val logger = Logger("FeedVM")

    init {
        loadFeedLogo()
    }

    fun loadFeedLogo(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productList.value = feedRepository.fetchProductList()
            } catch (e: Exception){
                logger.error(e)
            }
        }
    }

    fun loadProductDetail(
        productId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productDetail.value = feedRepository.getProductById(productId)
            } catch (e: Exception) {
                logger.error(e)
            }
        }
    }
}
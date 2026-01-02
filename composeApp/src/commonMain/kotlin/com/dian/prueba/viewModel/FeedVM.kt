package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dian.prueba.model.ProductUIModel
import com.dian.prueba.repository.FeedRepository
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FeedVM(
    private val feedRepository: FeedRepository
): ViewModel() {
    private val _productList = MutableStateFlow<List<ProductUIModel>>(emptyList())
    val productList: MutableStateFlow<List<ProductUIModel>> get() = _productList
    private val logger = Logger("FeedVM")

    init {
        loadFeedLogo()
    }

    fun loadFeedLogo(){
        viewModelScope.launch {
            try {
                _productList.value = feedRepository.fetchProductList()
            } catch (e: Exception){
                logger.error(e)
            }
        }
    }
}

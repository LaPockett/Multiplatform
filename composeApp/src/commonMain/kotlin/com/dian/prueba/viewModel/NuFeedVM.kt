package com.dian.prueba.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dian.prueba.domain.nuFeed.model.NuFeedUIModel
import com.dian.prueba.data.nuFeed.mapper.toUIModel
import com.dian.prueba.repository.UserRepository
import com.dian.prueba.repository.NuFeedRepository
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

class NuFeedVM(
    private val nuFeedRepository: NuFeedRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _feedItems = MutableStateFlow<List<NuFeedUIModel>>(emptyList())
    val feedItems: StateFlow<List<NuFeedUIModel>> = _feedItems

    private var nextIndex: Int = 0
    private var hasMore: Boolean = true
    @Volatile
    private var isLoading: Boolean = false

    // TODO: UserID (need to implement)
    private val userId = "0"

    private val _featureFlags = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val featureFlags: StateFlow<Map<String, Boolean>> = _featureFlags

    private val _currentRoute = MutableStateFlow<String?>(null)

    private val _requiredActions = MutableStateFlow<List<String>>(emptyList())
    val requiredActions: StateFlow<List<String>> = _requiredActions
    private val logger = Logger("NuFeedVM")

    init {
        loadNextPage()
        startFeatureFlagPolling(10000L)
        startCurrentRoutePolling(5000L)
    }

    fun startFeatureFlagPolling(intervalMs : Long) {
        viewModelScope.launch(Dispatchers.IO){
            while (true) {
                logger.warn("=== Starting polling (featureFlags) ===")
                loadFeatureFlags()
                delay(intervalMs)
            }
        }
    }
    fun updateCurrentRoute(route: String?) {
        _currentRoute.value = route
    }

    fun startCurrentRoutePolling(intervalMs: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                logger.warn("=== Starting polling (currentRoute) ===")
                getCurrentRoute(_currentRoute.value)
                delay(intervalMs)
            }
        }
    }

    fun getCurrentRoute(currentRoute: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.getCurrentRoute(currentRoute, userId)
            } catch (e: Exception) {
                logger.error(e)
            }
        }
    }
    fun loadNextPage() {
        if (isLoading || !hasMore) return
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            try {
                val response = nuFeedRepository.getNuFeed(nextIndex)
                val newItems = response.feed
                    .mapNotNull { it.toUIModel() }
                _feedItems.update { current -> current + newItems }
                logger.warn("Total items: ${response.feed.size}, UI items: ${newItems.size}")
                logger.warn("New Items $newItems")
                logger.warn("Next index: ${response.next_index}, hasMore: ${response.has_more}")
                nextIndex = response.next_index
                hasMore = response.has_more
            } catch (e: Exception) {
                logger.error(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadFeatureFlags() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = userRepository.getFeatureFlags(userId)
                _featureFlags.update { response.flags }
                _requiredActions.update { response.requiredActions }
                logger.warn("Feature flags loaded: ${response.flags}")
            } catch (e: Exception){
                logger.error(e)
            }
        }
    }
}
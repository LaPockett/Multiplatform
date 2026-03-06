package com.dian.prueba.viewModel

import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.data.nuFeed.enums.AssetType
import com.dian.prueba.data.nuFeed.model.Feed
import com.dian.prueba.data.nuFeed.model.NuFeedResponse
import com.dian.prueba.domain.feed.model.FeedItemUI
import com.dian.prueba.domain.feed.model.ProductUIModel
import com.dian.prueba.domain.product.model.ProductDetailUIModel
import com.dian.prueba.network.service.FeatureFlagAPIService
import com.dian.prueba.network.service.FeedAPIService
import com.dian.prueba.network.service.NuFeedAPIService
import com.dian.prueba.network.service.ProductAPIService
import com.dian.prueba.repository.FeatureFlagRepositoryImpl
import com.dian.prueba.repository.NuFeedRepositoryImpl
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.Exception
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

//*  === Fake de FeedAPIService ===
class FakeFeedAPIService : FeedAPIService {
    override suspend fun getProductList(): List<ProductUIModel> {
        return listOf(
            ProductUIModel(
                imageUrl = "https://cdn.shopify.com/s/files/1/0",
                urlVideo = "example.video",
                assetType = AssetMediaType.IMAGE,
                productId = "123abc",
                posterVariants = emptyList(),
                feedItem = FeedItemUI(
                    isPremium = false,
                    isFavorite = false,
                    assetUrl = "https://cdn.shopify.",
                    productId = "123abc",
                    variantId = "333abc"
                )
            )
        )
    }
}

//* === Fake de NuFeedAPIService ===
class FakeNuFeedAPIService : NuFeedAPIService {
    override suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse {
        if (paginationIndex >= 0) {
            return NuFeedResponse(
                feed = listOf(
                    Feed(type = AssetType.TILE, body = "body", actions = emptyList(),
                        product = null, asset = null, large = null,
                        isPremium = true, isFavorite = false),
                    Feed(type = AssetType.MESSAGE_IN, body = "ydob", actions = emptyList(),
                        product = null, asset = null, large = null,
                        isPremium = true, isFavorite = true)
                ),
                has_more = false,
                next_index = 0
            )
        } else {
            throw IllegalArgumentException("Pagination index is less than 0")
        }
    }
}

//* === Fake de ProductAPIService ===
class FakeProductAPIService : ProductAPIService {
    override suspend fun getProductById(productId: String): ProductDetailUIModel? {
        return if (productId.isNotEmpty()) {
            ProductDetailUIModel(
                id = "123abc",
                brand = "Nike",
                manufacturingCountry = "China",
                productName = "Nike Air Max",
                storyTelling = "Good product ;)",
                styleIt = "Nice design",
                type = "Shoes",
                variants = emptyList()
            )
        } else null
    }
}

//* === Fake de FeatureFlagAPIService ===
class FakeFeatureFlagAPIService : FeatureFlagAPIService {
    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse {
        if (userId.isNotEmpty()) {
            return FeatureFlagsResponse(
                requiredActions = listOf("action1", "action2", "action3"),
                flags = mapOf("feature1" to true, "feature2" to false, "feature3" to true)
            )
        } else {
            throw Exception("User id is empty")
        }
    }
}

class NuFeedVMTest {
    private val logger = Logger("NuFeedVMTest")

    private lateinit var fakeFeedService: FakeFeedAPIService
    private lateinit var fakeNuFeedService: FakeNuFeedAPIService
    private lateinit var fakeProductService: FakeProductAPIService
    private lateinit var fakeFeatureFlagService: FakeFeatureFlagAPIService
    private lateinit var nuFeedVM: NuFeedVM

    @Before
    fun setUp() {
        logger.warn("\n=== Set up NuFeedVMTest ===")
        fakeFeedService = FakeFeedAPIService()
        fakeNuFeedService = FakeNuFeedAPIService()
        fakeProductService = FakeProductAPIService()
        fakeFeatureFlagService = FakeFeatureFlagAPIService()
        nuFeedVM = NuFeedVM(
            nuFeedRepository = NuFeedRepositoryImpl(fakeNuFeedService),
            featureFlagRepository = FeatureFlagRepositoryImpl(fakeFeatureFlagService)
        )
    }

    @Test
    fun `get nuFeed successfully`() = runTest {
        val nuFeed = fakeNuFeedService.getNuFeed(1)
        assert(nuFeed.feed.isNotEmpty())
    }

    @Test
    fun `get nuFeed unsuccessfully because pagination index is less than 0` () = runTest {
        val exception = assertFailsWith<IllegalArgumentException> {
            fakeNuFeedService.getNuFeed(-1)
        }
        assertEquals("Pagination index is less than 0", exception.message)
    }

    @Test
    fun `get product list` () = runTest {
        val list = fakeFeedService.getProductList()
        assert(list.isNotEmpty())
    }

    @Test
    fun `get product by valid id`() = runTest {
        val product = fakeProductService.getProductById("123abc")
        assert(product?.brand == "Nike")
    }

    @Test
    fun `get product by invalid id`() = runTest {
        val product = fakeProductService.getProductById("")
        assert(product == null)
    }

    @Test
    fun `get featureFlags successfully`() = runTest {
        val featureFlags = fakeFeatureFlagService.getFeatureFlags("3")
        assert(featureFlags.flags.isNotEmpty())
    }

    @Test
    fun `get featureFlags unsuccessfully`() = runTest {
        val exception = assertFailsWith<Exception> {
            fakeFeatureFlagService.getFeatureFlags("")
        }
        assertEquals("User id is empty", exception.message)
    }
}
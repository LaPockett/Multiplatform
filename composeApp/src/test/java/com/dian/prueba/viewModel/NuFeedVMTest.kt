package com.dian.prueba.viewModel

import com.dian.prueba.data.feed.enums.AssetMediaType
import com.dian.prueba.data.feed.model.AssetResponse
import com.dian.prueba.data.feed.mapper.FeedItemUI
import com.dian.prueba.data.feed.model.Product
import com.dian.prueba.data.feed.mapper.ProductUIModel
import com.dian.prueba.data.featureFlag.model.FeatureFlagsResponse
import com.dian.prueba.data.nuFeed.enums.AssetType
import com.dian.prueba.data.nuFeed.model.Feed
import com.dian.prueba.data.nuFeed.model.NuFeedResponse
import com.dian.prueba.data.product.mapper.ProductDetailUIModel
import com.dian.prueba.network.LogoAPIService
import com.dian.prueba.repository.FeedRepository
import com.dian.prueba.repository.FeedRepositoryImpl
import com.dian.prueba.utilities.Logger
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.Exception
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FakeLogoAPIService : LogoAPIService {
    override suspend fun getProductList(): List<ProductUIModel> {
        val listProducts = listOf(
            ProductUIModel(
                imageUrl = "https://cdn.shopify.com/s/files/1/0",
                urlVideo = "example.video",
                assetType = AssetMediaType.IMAGE,
                productId = "123abc",
                posterVariants = emptyList(),
                feedItem = FeedItemUI(
                    isPremium = false,
                    isFavorite = false,
                    asset = AssetResponse(
                        url = "https://cdn.shopify.",
                        type = AssetMediaType.IMAGE,
                        posterVariants = emptyList(),
                        variants = emptyList()
                    ),
                    product = Product(
                        product = "123abc",
                        variant = "333abc"
                    )
                )
        ), ProductUIModel(
                imageUrl = "https://cdn.shopify.com/s/files/1/0",
                urlVideo = "example.video",
                assetType = AssetMediaType.IMAGE,
                productId = "123abc",
                posterVariants = emptyList(),
                feedItem = FeedItemUI(
                    isPremium = false,
                    isFavorite = false,
                    asset = AssetResponse(
                        url = "https://cdn.shopify.",
                        type = AssetMediaType.IMAGE,
                        posterVariants = emptyList(),
                        variants = emptyList()
                    ),
                    product = Product(
                        product = "123abc",
                        variant = "333abc"
                    )
                )
            ),
            ProductUIModel(
                imageUrl = "https://cdn.shopify.com/s/files/1/0",
                urlVideo = "example.video",
                assetType = AssetMediaType.IMAGE,
                productId = "123abc",
                posterVariants = emptyList(),
                feedItem = FeedItemUI(
                    isPremium = false,
                    isFavorite = false,
                    asset = AssetResponse(
                        url = "https://cdn.shopify.",
                        type = AssetMediaType.IMAGE,
                        posterVariants = emptyList(),
                        variants = emptyList()
                    ),
                    product = Product(
                        product = "123abc",
                        variant = "333abc"
                    )
                )
            ),
            ProductUIModel(
                imageUrl = "https://cdn.shopify.com/s/files/1/0",
                urlVideo = "example.video",
                assetType = AssetMediaType.IMAGE,
                productId = "123abc",
                posterVariants = emptyList(),
                feedItem = FeedItemUI(
                    isPremium = false,
                    isFavorite = false,
                    asset = AssetResponse(
                        url = "https://cdn.shopify.",
                        type = AssetMediaType.IMAGE,
                        posterVariants = emptyList(),
                        variants = emptyList()
                    ),
                    product = Product(
                        product = "123abc",
                        variant = "333abc"
                    )
                )
            )
        )
        return listProducts
    }

    override suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse {
        if (paginationIndex >= 0) {
            return NuFeedResponse(
                feed = listOf(
                    Feed(
                        type = AssetType.TILE,
                        body = "body",
                        actions = emptyList(),
                        product = null,
                        asset = null,
                        large = null,
                        isPremium = true,
                        isFavorite = false
                    ),
                    Feed(
                        type = AssetType.MESSAGE_IN,
                        body = "ydob",
                        actions = emptyList(),
                        product = null,
                        asset = null,
                        large = null,
                        isPremium = true,
                        isFavorite = true
                    )
                ),
                has_more = false,
                next_index = 0
            )
        } else {
            throw IllegalArgumentException("Pagination index is less than 0")
        }
    }

    override suspend fun getProductById(productId: String): ProductDetailUIModel? {
        return if (productId.isNotEmpty()) {
            ProductDetailUIModel(
                _id = "123abc",
                brand = "Nike",
                manufacturingCountry = "China",
                productName = "Nike Air Max",
                storyTelling = "Good product ;)",
                styleIt = "Nice design",
                type = "Shoes",
                variants = emptyList()
            )
        } else {
            null
        }
    }

    override suspend fun getFeatureFlags(userId: String): FeatureFlagsResponse {
        if (userId.isNotEmpty()){
            return FeatureFlagsResponse(
                requiredActions = listOf<String>("action1", "action2", "action3"),
                flags = mapOf("feature1" to true , "feature2" to false, "feature3" to true)
            )
        } else {
            throw Exception("User id is empty")
        }
    }

}
class NuFeedVMTest {
    val logger = Logger("NuFeedVMTest")
    @get:Rule
    private lateinit var fakeLogoAPIService: FakeLogoAPIService
    private lateinit var nuFeedVM: NuFeedVM
    private lateinit var feedRepository: FeedRepository

    @Before
    fun setUp() {
        logger.warn("\n=== Set up NuFeedVMTest ===")
        fakeLogoAPIService = FakeLogoAPIService()
        feedRepository = FeedRepositoryImpl(fakeLogoAPIService)
        nuFeedVM = NuFeedVM(feedRepository)
    }

    @Test
    fun `get nuFeed successfully`() = runTest {
        logger.warn("\n=== Get nuFeed successfully ===")
        val nuFeed = fakeLogoAPIService.getNuFeed(1)
        logger.debug("NuFeed: $nuFeed")
        assert(nuFeed.feed.isNotEmpty())
    }
    //Fails
    @Test
    fun `get nuFeed unsuccessfully because pagination index is less than 0` () = runTest {
        logger.warn("\n=== Get nuFeed unsuccessfully ===")
        val nuFeed = fakeLogoAPIService.getNuFeed(-1)
        logger.debug("NuFeed: $nuFeed")
        val exception = assertFailsWith<IllegalArgumentException> { fakeLogoAPIService.getNuFeed(-1) }
        assertEquals("Pagination index is less than 0", exception.message)
    }

    @Test
    fun `get product list ` () = runTest {
        logger.warn("\n=== Get product list ===")
        val list = fakeLogoAPIService.getProductList()
        logger.debug("Product list: $list")
        assert(list.isNotEmpty())
    }

    @Test
    fun `get product by valid id`() = runTest {
        logger.warn("\n=== Get product by valid id ===")
        val productDetails = fakeLogoAPIService.getProductById("123abc")
        logger.debug("Product details: $productDetails")
        assert(productDetails?.brand== "Nike")
    }
    @Test
    fun `get product by invalid id`() = runTest {
        logger.warn("\n=== Get product by invalid id ===")
        val productDetails = fakeLogoAPIService.getProductById("")
        logger.debug("Product details: $productDetails")
        assert(productDetails == null)
    }

    @Test
    fun `get featureFlags successfully`() = runTest {
        logger.warn("\n=== Get feature flags successfully ===")
        val featureFlags = fakeLogoAPIService.getFeatureFlags("3")
        logger.debug("Feature flags: $featureFlags")
        assert(featureFlags.flags.isNotEmpty())
    }

    //Fails
    @Test
    fun `get featureFlags unsuccessfully`() = runTest {
        logger.warn("\n=== Get feature flags unsuccessfully===")
        val featureFlags = fakeLogoAPIService.getFeatureFlags("")
        logger.debug("Feature flags: $featureFlags")
        val exception = assertFailsWith<Exception> { fakeLogoAPIService.getFeatureFlags("") }
        assertEquals("User id is empty", exception.message)
    }
}
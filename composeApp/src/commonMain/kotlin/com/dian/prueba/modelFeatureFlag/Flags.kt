package com.dian.prueba.modelFeatureFlag

import kotlinx.serialization.Serializable

@Serializable
data class Flags(
    val videosInFeed: Boolean
)

@Serializable
data class FeatureFlagsResponse(
    val requiredActions: List<String>,
    val flags: Map<String, Boolean>
)
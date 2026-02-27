package com.dian.prueba.data.featureFlag.model

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

@Serializable
data class FeatureFlagValue(
    val value: Boolean
)
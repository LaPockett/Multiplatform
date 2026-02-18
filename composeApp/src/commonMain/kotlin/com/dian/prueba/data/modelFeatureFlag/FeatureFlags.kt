package com.dian.prueba.data.modelFeatureFlag

import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlags(
    val flags: Flags,
    val requiredActions: List<String>
)
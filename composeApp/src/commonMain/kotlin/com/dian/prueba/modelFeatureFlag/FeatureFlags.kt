package com.dian.prueba.modelFeatureFlag

import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlags(
    val flags: Flags,
    val requiredActions: List<String>
)
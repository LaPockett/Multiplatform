package com.dian.prueba.data.featureFlag.model

import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlags(
    val flags: Flags,
    val requiredActions: List<String>
)
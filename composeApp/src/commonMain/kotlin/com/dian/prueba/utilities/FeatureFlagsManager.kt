package com.dian.prueba.utilities

object FeatureFlagsManager{
    private val logger = Logger("FeatureFlagsManager")
    private var featureFlags: Map<String, Boolean> = emptyMap()
    fun update(flags: Map<String, Boolean>) {
        logger.warn("Updating feature flags: $flags")
        featureFlags = flags
    }
    fun getData(key: String): Boolean {
        logger.warn("Getting data for key: $key")
        return featureFlags[key] ?: false
    }
}
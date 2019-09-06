package com.syjgin.fieldgenerator.config

import android.content.SharedPreferences

object ConfigStorage {
    enum class HighKey {
        Cloud,
        NoCloud
    }

    enum class MiddleKey {
        FieldType,
        Danger,
        Enemy
    }

    enum class LowKey {
        Wind,
        Floatage,
        Lightning,
        Enemy,
        Zeppelin,
        Whale,
        Spider,
        GlassWings
    }

    data class ConfigKey(val highKey: HighKey, val middleKey: MiddleKey, val lowKey: LowKey) {
        override fun equals(other: Any?): Boolean {
            if(other == null)
                return false
            if(other !is ConfigKey)
                return false
            return other.highKey == highKey && other.middleKey == middleKey && other.lowKey == lowKey
        }

        override fun toString(): String {
            return highKey.name + middleKey.name + lowKey.name
        }
    }

    val predefinedKeys : Map<ConfigKey, Int> by lazy { mapOf(
        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.FieldType,
                LowKey.Wind
            ), 50),
        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.FieldType,
                LowKey.Floatage
            ), 35),

        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Danger,
                LowKey.Lightning
            ), 33),
        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Danger,
                LowKey.Enemy
            ), 33),

        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.Zeppelin
            ), 50),
        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.Whale
            ), 17),
        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.GlassWings
            ), 17),
        Pair(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.Spider
            ), 17),

        Pair(
            ConfigKey(
                HighKey.NoCloud,
                MiddleKey.FieldType,
                LowKey.Wind
            ), 65),
        Pair(
            ConfigKey(
                HighKey.NoCloud,
                MiddleKey.FieldType,
                LowKey.Floatage
            ), 35),
        Pair(
            ConfigKey(
                HighKey.NoCloud,
                MiddleKey.Danger,
                LowKey.Lightning
            ), 17)
    ) }

    fun reset(prefs: SharedPreferences) {
        prefs.edit().clear().apply()
    }

    fun getCloudConfig(prefs: SharedPreferences) : CloudConfig {
        val windPossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.FieldType,
                LowKey.Wind
            ), prefs
        )
        val floatagePossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.FieldType,
                LowKey.Floatage
            ), prefs
        )
        val fieldType =
            FieldTypeConfig(windPossibility, floatagePossibility)
        val lightningPossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Danger,
                LowKey.Lightning
            ), prefs
        )
        val enemyPossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Danger,
                LowKey.Enemy
            ), prefs
        )
        val cloudDanger =
            CloudDangerConfig(lightningPossibility, enemyPossibility)
        val zeppelinPossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.Zeppelin
            ), prefs
        )
        val whalePossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.Whale
            ), prefs
        )
        val spiderPossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.Spider
            ), prefs
        )
        val glassWingsPossibility = getConfigValue(
            ConfigKey(
                HighKey.Cloud,
                MiddleKey.Enemy,
                LowKey.GlassWings
            ), prefs
        )
        val enemy = CloudEnemyConfig(
            zeppelinPossibility,
            spiderPossibility,
            whalePossibility,
            glassWingsPossibility
        )
        return CloudConfig(fieldType, cloudDanger, enemy)
    }

    fun getFieldConfig(prefs: SharedPreferences) : FieldConfig {
        val windPossibility = getConfigValue(
            ConfigKey(
                HighKey.NoCloud,
                MiddleKey.FieldType,
                LowKey.Wind
            ), prefs
        )
        val floatagePossibility = getConfigValue(
            ConfigKey(
                HighKey.NoCloud,
                MiddleKey.FieldType,
                LowKey.Floatage
            ), prefs
        )
        val fieldType =
            FieldTypeConfig(windPossibility, floatagePossibility)
        val lighningPossibility = getConfigValue(
            ConfigKey(
                HighKey.NoCloud,
                MiddleKey.Danger,
                LowKey.Lightning
            ), prefs
        )
        return FieldConfig(fieldType, lighningPossibility)
    }

    fun getConfigValue(key: ConfigKey, prefs: SharedPreferences) : Int {
        val stringKey = key.toString()
        return if(!prefs.contains(stringKey)) {
            if(predefinedKeys.contains(key)) {
                predefinedKeys[key] ?: 0
            } else 0
        } else prefs.getInt(stringKey, 0)
    }

    public fun setConfigValue(key: ConfigKey, prefs: SharedPreferences, value: Int) {
        prefs.edit().putInt(key.toString(), value).apply()
    }
}
package com.syjgin.fieldgenerator.config

data class CloudConfig(
    val cloudType: FieldTypeConfig,
    val danger: CloudDangerConfig,
    val enemies: CloudEnemyConfig
    )
package com.syjgin.fieldgenerator.config

data class DependentFieldConfig(
    val fieldType: FieldTypeConfig,
    val dangerPossibility: Int,
    val windConfig: WindConfig
)
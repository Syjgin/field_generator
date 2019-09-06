package com.syjgin.fieldgenerator.generator

data class GeneratedDependentField(
    val fieldType: FieldEnum,
    val floatageType: FloatageEnum,
    val isLightning: Boolean,
    val direction: WindDirectionEnum,
    val isHigher: Boolean)
package com.syjgin.fieldgenerator.generator

data class GeneratedField(
    val fieldType: FieldEnum,
    val windType: WindEnum,
    val floatageType: FloatageEnum,
    val dangerType: DangerEnum,
    val enemyType: EnemyEnum,
    val windVelocity: Int,
    val weaponType: WeaponEnum,
    val zeppelinType: ZeppelinEnum
)
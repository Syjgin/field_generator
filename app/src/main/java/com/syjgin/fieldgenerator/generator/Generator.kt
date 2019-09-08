package com.syjgin.fieldgenerator.generator

import android.util.Log
import com.syjgin.fieldgenerator.config.CloudConfig
import com.syjgin.fieldgenerator.config.FieldConfig
import kotlin.random.Random

object Generator {
    const val PERCENT = 100

    private val random : Random by lazy { Random(System.currentTimeMillis()) }

    fun generateCloud(config: CloudConfig) : GeneratedField {
        var resultWind = WindEnum.Nothing
        var resultFloatage = FloatageEnum.Nothing
        val resultDanger: DangerEnum
        var resultEnemy = EnemyEnum.Nothing
        var resultVelocity = 0
        val cloudType = mutableListOf<Pair<Int, FieldEnum>>()
        cloudType.add(Pair(config.cloudType.floatagePossibility,
            FieldEnum.Floatage
        ))
        cloudType.add(Pair(
            PERCENT - (config.cloudType.floatagePossibility + config.cloudType.windPossibility),
            FieldEnum.Empty
        ))
        cloudType.add(Pair(config.cloudType.windPossibility,
            FieldEnum.Wind
        ))
        val resultCloudType =
            selectVariantByPossibilities(cloudType)
        when(resultCloudType) {
            FieldEnum.Wind -> {
                resultWind = generateWind()
                resultVelocity = generateWindVelocity()
            }
            FieldEnum.Floatage -> {
                resultFloatage = generateFloatage()
            }
            FieldEnum.Empty -> {}
        }
        val dangerType = mutableListOf<Pair<Int, DangerEnum>>()
        dangerType.add(Pair(config.danger.enemyPossibility,
            DangerEnum.Enemy
        ))
        dangerType.add(Pair(config.danger.lightningPossibility,
            DangerEnum.Lightning
        ))
        dangerType.add(Pair(
            PERCENT - (config.danger.enemyPossibility + config.danger.lightningPossibility),
            DangerEnum.Nothing
        ))
        resultDanger =
            selectVariantByPossibilities(dangerType)
        if(resultDanger == DangerEnum.Enemy) {
            val enemyType = mutableListOf<Pair<Int, EnemyEnum>>()
            enemyType.add(Pair(config.enemies.zeppelinPossibility,
                EnemyEnum.Zeppelin
            ))
            enemyType.add(Pair(config.enemies.whalePossibility,
                EnemyEnum.Whale
            ))
            enemyType.add(Pair(config.enemies.glassWingsPossibility,
                EnemyEnum.GlassWings
            ))
            enemyType.add(Pair(config.enemies.spiderPossibility,
                EnemyEnum.Spider
            ))
            resultEnemy =
                selectVariantByPossibilities(enemyType)
        }
        val result = GeneratedField(
            resultCloudType,
            resultWind,
            resultFloatage,
            resultDanger,
            resultEnemy,
            resultVelocity
        )
        Log.d(javaClass.canonicalName, result.toString())
        return result
    }

    fun generateIndependentField(config: FieldConfig) : GeneratedField {
        val fieldType = mutableListOf<Pair<Int, FieldEnum>>()
        fieldType.add(Pair(config.fieldType.floatagePossibility,
            FieldEnum.Floatage
        ))
        fieldType.add(Pair(config.fieldType.windPossibility,
            FieldEnum.Wind
        ))
        var resultVelocity = 0
        val resultType =
            selectVariantByPossibilities(fieldType)
        var resultWind = WindEnum.Nothing
        var resultFloatage = FloatageEnum.Nothing
        when(resultType) {
            FieldEnum.Wind -> {
                resultWind = generateWind()
                resultVelocity = generateWindVelocity()
            }
            FieldEnum.Floatage -> {
                resultFloatage = generateFloatage()
            }
            FieldEnum.Empty -> {}
        }
        val dangerType = mutableListOf<Pair<Int, DangerEnum>>()
        dangerType.add(Pair(config.dangerPossibility,
            DangerEnum.Lightning
        ))
        dangerType.add(Pair(
            PERCENT - config.dangerPossibility,
            DangerEnum.Nothing
        ))
        val resultDanger =
            selectVariantByPossibilities(dangerType)
        val result = GeneratedField(
            resultType,
            resultWind,
            resultFloatage,
            resultDanger,
            EnemyEnum.Nothing,
            resultVelocity
        )
        Log.d(javaClass.canonicalName, result.toString())
        return result
    }

    fun generateDependentField(config: FieldConfig) : GeneratedDependentField {
        val fieldType = mutableListOf<Pair<Int, FieldEnum>>()
        fieldType.add(Pair(config.fieldType.floatagePossibility,
            FieldEnum.Floatage
        ))
        fieldType.add(Pair(config.fieldType.windPossibility,
            FieldEnum.Wind
        ))
        val resultType =
            selectVariantByPossibilities(fieldType)
        var resultFloatage = FloatageEnum.Nothing
        var resultDirection = WindDirectionEnum.Same
        var resultWindHigher = false
        when(resultType) {
            FieldEnum.Wind -> {
                val direction = mutableListOf<Pair<Int, WindDirectionEnum>>()
                direction.add(Pair(1, WindDirectionEnum.Same))
                direction.add(Pair(1, WindDirectionEnum.Left))
                direction.add(Pair(1, WindDirectionEnum.Right))
                resultDirection =
                    selectVariantByPossibilities(
                        direction
                    )
                val higher = mutableListOf<Pair<Int, Boolean>>()
                higher.add(Pair(1, true))
                higher.add(Pair(1, false))
                resultWindHigher =
                    selectVariantByPossibilities(
                        higher
                    )
            }
            FieldEnum.Floatage -> {
                resultFloatage = generateFloatage()
            }
            FieldEnum.Empty -> {}
        }
        val dangerType = mutableListOf<Pair<Int, DangerEnum>>()
        dangerType.add(Pair(config.dangerPossibility,
            DangerEnum.Lightning
        ))
        dangerType.add(Pair(
            PERCENT - config.dangerPossibility,
            DangerEnum.Nothing
        ))
        val resultDanger =
            selectVariantByPossibilities(dangerType)
        val result = GeneratedDependentField(
            resultType,
            resultFloatage,
            resultDanger == DangerEnum.Lightning,
            resultDirection,
            resultWindHigher
        )
        Log.d(javaClass.canonicalName, result.toString())
        return result
    }

    private fun generateFloatage() : FloatageEnum {
        val floatage = mutableListOf<Pair<Int, FloatageEnum>>()
        floatage.add(Pair(1, FloatageEnum.Negative))
        floatage.add(Pair(1, FloatageEnum.Positive))
        return selectVariantByPossibilities(floatage)
    }

    private fun generateWind() : WindEnum {
        val winds = mutableListOf<Pair<Int, WindEnum>>()
        WindEnum.values().forEach {
            if(it != WindEnum.Nothing)
                winds.add(Pair(1, it))
        }
        return selectVariantByPossibilities(winds)
    }

    private fun generateWindVelocity() : Int {
        val velocities = mutableListOf<Pair<Int,Int>>()
        velocities.add(Pair(1,1))
        velocities.add(Pair(1,2))
        velocities.add(Pair(1,3))
        return selectVariantByPossibilities(velocities)
    }

    private fun <T>selectVariantByPossibilities(possibilities : List<Pair<Int, T>>) : T {
        val possibilityValues =
            createPossibilitiesArray(possibilities)
        val variant = random.nextInt(possibilityValues.size)
        return possibilityValues[variant]
    }

    fun <T>createPossibilitiesArray(possibilities: List<Pair<Int,T>>): ArrayList<T> {
        val possibilityValues = arrayListOf<T>()
        for(i in possibilities.indices) {
            val count = possibilities[i].first
            val currentObject = possibilities[i].second
            for(j in 0 until count) {
                possibilityValues.add(currentObject)
            }
        }
        return possibilityValues
    }
}
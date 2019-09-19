package com.syjgin.fieldgenerator.generator

import android.util.Log
import com.syjgin.fieldgenerator.config.CloudConfig
import com.syjgin.fieldgenerator.config.DependentFieldConfig
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
        var resultZeppelin = ZeppelinEnum.Nothing
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
            if(resultEnemy == EnemyEnum.Zeppelin) {
                resultZeppelin = generateZeppelin()
            }
        }
        val result = GeneratedField(
            resultCloudType,
            resultWind,
            resultFloatage,
            resultDanger,
            resultEnemy,
            resultVelocity,
            generateWeapon(),
            resultZeppelin
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
            resultVelocity,
            WeaponEnum.Nothing,
            ZeppelinEnum.Nothing
        )
        Log.d(javaClass.canonicalName, result.toString())
        return result
    }

    fun generateDependentField(config: DependentFieldConfig): GeneratedDependentField {
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
        var resultVelocity = 0
        var resultWind = WindEnum.Nothing
        when(resultType) {
            FieldEnum.Wind -> {
                resultVelocity = generateWindVelocity()
                val direction = mutableListOf<Pair<Int, WindEnum>>()
                direction.add(Pair(config.windConfig.tailWindPossibility, WindEnum.Top))
                direction.add(Pair(config.windConfig.tailSideWindPossibility / 2, WindEnum.TopLeft))
                direction.add(
                    Pair(
                        config.windConfig.tailSideWindPossibility / 2,
                        WindEnum.TopRight
                    )
                )
                direction.add(Pair(config.windConfig.headWindPossibility, WindEnum.Bottom))
                direction.add(
                    Pair(
                        config.windConfig.headSideWindPossibility / 2,
                        WindEnum.BottomRight
                    )
                )
                direction.add(
                    Pair(
                        config.windConfig.headSideWindPossibility / 2,
                        WindEnum.BottomLeft
                    )
                )
                resultWind =
                    selectVariantByPossibilities(
                        direction
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
            resultWind,
            resultVelocity
        )
        Log.d(javaClass.canonicalName, result.toString())
        return result
    }

    private fun generateFloatage() : FloatageEnum {
        val floatage = listOf(FloatageEnum.Negative, FloatageEnum.Positive)
        return selectVariantByEqualPossibilities(floatage)
    }

    private fun generateWind() : WindEnum {
        val winds = listOf(WindEnum.Bottom, WindEnum.BottomLeft, WindEnum.BottomRight, WindEnum.TopRight, WindEnum.Top, WindEnum.TopLeft)
        return selectVariantByEqualPossibilities(winds)
    }

    private fun generateZeppelin() : ZeppelinEnum {
        val zeppelins = listOf(ZeppelinEnum.Pirate, ZeppelinEnum.Trader, ZeppelinEnum.Whalebot)
        return selectVariantByEqualPossibilities(zeppelins)
    }

    private fun generateWindVelocity() : Int {
        val list = listOf(1,2,3)
        return selectVariantByEqualPossibilities(list)
    }

    private fun generateWeapon() : WeaponEnum {
        return selectVariantByEqualPossibilities(listOf(WeaponEnum.Left, WeaponEnum.Right, WeaponEnum.Under, WeaponEnum.Upper))
    }

    private fun <T>selectVariantByPossibilities(possibilities : List<Pair<Int, T>>) : T {
        val possibilityValues =
            createPossibilitiesArray(possibilities)
        val variant = random.nextInt(possibilityValues.size)
        return possibilityValues[variant]
    }

    private fun <T>selectVariantByEqualPossibilities(possibilities : List<T>) : T {
        val variant = random.nextInt(possibilities.size)
        return possibilities[variant]
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
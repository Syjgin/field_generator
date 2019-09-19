package com.syjgin.fieldgenerator.ui

import android.content.Context
import com.syjgin.fieldgenerator.R
import com.syjgin.fieldgenerator.generator.*

object GeneratedFieldRenderer {
    fun renderField(field: GeneratedField, context: Context) : Pair<String, Int?> {
        var resultIcon : Int? = null
        val builder = StringBuilder()
        builder.append(context.getString(R.string.field_type))
        when(field.fieldType) {
            FieldEnum.Wind -> {
                builder.append(context.getString(R.string.wind))
                resultIcon = when(field.windType) {
                    WindEnum.Top -> R.drawable.ic_top
                    WindEnum.TopRight -> R.drawable.ic_top_right
                    WindEnum.TopLeft -> R.drawable.ic_top_left
                    WindEnum.Bottom -> R.drawable.ic_bottom
                    WindEnum.BottomRight -> R.drawable.ic_bottom_right
                    WindEnum.BottomLeft -> R.drawable.ic_bottom_left
                    WindEnum.Nothing -> null
                }
                builder.append("\n")
                builder.append(context.getString(R.string.velocity))
                builder.append(field.windVelocity)
            }
            FieldEnum.Floatage -> {
                builder.append(context.getString(R.string.floatage))
                resultIcon = when(field.floatageType) {
                    FloatageEnum.Positive -> R.drawable.ic_top
                    FloatageEnum.Negative -> R.drawable.ic_bottom
                    FloatageEnum.Nothing -> null
                }
            }
            FieldEnum.Empty -> {
                builder.append(context.getString(R.string.empty))
            }
        }
        when(field.dangerType) {
            DangerEnum.Lightning -> {
                builder.append("\n")
                builder.append(context.getString(R.string.danger))
                builder.append(context.getString(R.string.ligntning))
            }
            DangerEnum.Enemy -> {
                builder.append("\n")
                builder.append(context.getString(R.string.danger))
                builder.append(context.getString(
                    when(field.enemyType) {
                        EnemyEnum.Zeppelin -> R.string.zeppelin
                        EnemyEnum.Spider -> R.string.spider
                        EnemyEnum.Whale -> R.string.whale
                        EnemyEnum.GlassWings -> R.string.glasswings
                        EnemyEnum.Nothing -> R.string.empty
                    }
                ))
                if(field.enemyType == EnemyEnum.Zeppelin) {
                    builder.append("\n")
                    builder.append(context.getString(R.string.weapon))
                    builder.append(context.getString(when(field.weaponType) {
                        WeaponEnum.Under -> R.string.under
                        WeaponEnum.Upper -> R.string.upper
                        WeaponEnum.Left -> R.string.left_weapon
                        WeaponEnum.Right -> R.string.right_weapon
                        WeaponEnum.Nothing -> R.string.none
                    }))
                    builder.append("\n")
                    builder.append(context.getString(R.string.zeppelin_type))
                    builder.append(context.getString(when(field.zeppelinType) {
                        ZeppelinEnum.Trader -> R.string.trader
                        ZeppelinEnum.Whalebot -> R.string.whalebot
                        ZeppelinEnum.Pirate -> R.string.pirate
                        ZeppelinEnum.Nothing -> R.string.none
                    }))
                }
            }
            DangerEnum.Nothing -> {}
        }
        return Pair(builder.toString(), resultIcon)
    }

    fun renderDependentField(field: GeneratedDependentField, context: Context) : Pair<String, Int?> {
        var resultIcon : Int? = null
        val builder = StringBuilder()
        builder.append(context.getString(R.string.field_type))
        when(field.fieldType) {
            FieldEnum.Wind -> {
                builder.append(context.getString(R.string.wind))
                builder.append(" ")
                builder.append(context.getString(R.string.velocity))
                builder.append(field.windVelocity)
                resultIcon = when (field.direction) {
                    WindEnum.Top -> R.drawable.ic_top
                    WindEnum.TopRight -> R.drawable.ic_top_right
                    WindEnum.TopLeft -> R.drawable.ic_top_left
                    WindEnum.Bottom -> R.drawable.ic_bottom
                    WindEnum.BottomRight -> R.drawable.ic_bottom_right
                    WindEnum.BottomLeft -> R.drawable.ic_bottom_left
                    WindEnum.Nothing -> null
                }
            }
            FieldEnum.Floatage -> {
                builder.append(context.getString(R.string.floatage))
                resultIcon = when(field.floatageType) {
                    FloatageEnum.Positive -> R.drawable.ic_top
                    FloatageEnum.Negative -> R.drawable.ic_bottom
                    FloatageEnum.Nothing -> null
                }
            }
            FieldEnum.Empty -> {
                builder.append(context.getString(R.string.empty))
            }
        }
        if(field.isLightning) {
            builder.append("\n")
            builder.append(context.getString(R.string.danger))
            builder.append(context.getString(R.string.ligntning))
        }
        return Pair(builder.toString(), resultIcon)
    }
}
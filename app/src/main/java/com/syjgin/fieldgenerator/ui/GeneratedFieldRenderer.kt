package com.syjgin.fieldgenerator.ui

import android.content.Context
import com.syjgin.fieldgenerator.R
import com.syjgin.fieldgenerator.generator.WindDirectionEnum
import com.syjgin.fieldgenerator.generator.WindEnum
import com.syjgin.fieldgenerator.generator.*
import java.lang.StringBuilder

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
                builder.append(context.getString(
                    if(field.isHigher) {
                        R.string.high_wind
                    } else {
                        R.string.low_wind
                    }
                ))
                builder.append("\n")
                builder.append(context.getString(R.string.direction))
                builder.append(context.getString(when(field.direction) {
                    WindDirectionEnum.Left -> R.string.left
                    WindDirectionEnum.Right -> R.string.right
                    WindDirectionEnum.Same -> R.string.same_wind
                }))
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
package com.syjgin.fieldgenerator.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TextView
import com.syjgin.fieldgenerator.R
import com.syjgin.fieldgenerator.config.ConfigStorage
import com.syjgin.fieldgenerator.generator.Generator
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()
        reset_button.setOnClickListener { reset() }
        normalizeSeekbars(cloud_wind_possibility, listOf(cloud_floatage_possibility))
        normalizeSeekbars(cloud_floatage_possibility, listOf(cloud_wind_possibility))
        normalizeSeekbars(cloud_lightning_possibility, listOf(cloud_enemy_possibility))
        normalizeSeekbars(cloud_enemy_possibility, listOf(cloud_lightning_possibility))
        normalizeSeekbars(cloud_zeppelin_possibility, listOf(cloud_whale_possibility, cloud_spider_possibility, cloud_glasswings_possibility))
        normalizeSeekbars(cloud_whale_possibility, listOf(cloud_zeppelin_possibility, cloud_spider_possibility, cloud_glasswings_possibility))
        normalizeSeekbars(cloud_spider_possibility, listOf(cloud_whale_possibility, cloud_zeppelin_possibility, cloud_glasswings_possibility))
        normalizeSeekbars(cloud_glasswings_possibility, listOf(cloud_whale_possibility, cloud_spider_possibility, cloud_zeppelin_possibility))
        normalizeSeekbars(nocloud_wind_possibility, listOf(nocloud_floatage_possibility))
        normalizeSeekbars(nocloud_floatage_possibility, listOf(nocloud_wind_possibility))
        normalizeSeekbars(nocloud_lightning_possibility, emptyList())
        normalizeSeekbars(
            nocloud_tail_wind_possibility,
            listOf(
                nocloud_tail_side_wind_possibility,
                nocloud_head_side_wind_possibility,
                nocloud_head_wind_possibility
            )
        )
        normalizeSeekbars(
            nocloud_head_wind_possibility,
            listOf(
                nocloud_tail_side_wind_possibility,
                nocloud_head_side_wind_possibility,
                nocloud_tail_wind_possibility
            )
        )
        normalizeSeekbars(
            nocloud_tail_side_wind_possibility,
            listOf(
                nocloud_tail_wind_possibility,
                nocloud_head_side_wind_possibility,
                nocloud_head_wind_possibility
            )
        )
        normalizeSeekbars(
            nocloud_head_side_wind_possibility,
            listOf(
                nocloud_tail_side_wind_possibility,
                nocloud_tail_wind_possibility,
                nocloud_head_wind_possibility
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.save -> {
                save()
                finish()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        applyActionToAllSeekbars { ConfigStorage.setConfigValue(getConfigKey(it)!!, prefs, it.progress) }
    }

    private fun reset() {
        ConfigStorage.reset(prefs)
        loadData()
    }

    private fun loadData() {
        applyActionToAllSeekbars { setDataForSeekbar(it) }
    }

    private fun applyActionToAllSeekbars(action: (SeekBar)->Unit) {
        for(i in listOf<SeekBar>(
            cloud_wind_possibility,
            cloud_floatage_possibility,
            cloud_lightning_possibility,
            cloud_enemy_possibility,
            cloud_zeppelin_possibility,
            cloud_whale_possibility,
            cloud_spider_possibility,
            cloud_glasswings_possibility,
            nocloud_wind_possibility,
            nocloud_floatage_possibility,
            nocloud_lightning_possibility,
            nocloud_head_wind_possibility,
            nocloud_head_side_wind_possibility,
            nocloud_tail_wind_possibility,
            nocloud_tail_side_wind_possibility
        )) {
            action(i)
        }
    }

    private fun setDataForSeekbar(seekBar: SeekBar) {
        val progress = ConfigStorage.getConfigValue(getConfigKey(seekBar)!!, prefs)
        seekBar.progress = progress
        updateSeekbarDescription(seekBar, progress)
    }

    private fun updateSeekbarDescription(seekBar: SeekBar, progress: Int) {
        getDependentField(seekBar)?.text =
            String.format("%s: %d", getString(getDependentDescription(seekBar)!!), progress)
    }

    private fun getConfigKey(seekBar: SeekBar) : ConfigStorage.ConfigKey? {
        return when(seekBar) {
            cloud_wind_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.FieldType, ConfigStorage.LowKey.Wind)
            cloud_floatage_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.FieldType, ConfigStorage.LowKey.Floatage)
            cloud_lightning_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.Danger, ConfigStorage.LowKey.Lightning)
            cloud_enemy_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.Danger, ConfigStorage.LowKey.Enemy)
            cloud_zeppelin_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.Enemy, ConfigStorage.LowKey.Zeppelin)
            cloud_whale_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.Enemy, ConfigStorage.LowKey.Whale)
            cloud_spider_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.Enemy, ConfigStorage.LowKey.Spider)
            cloud_glasswings_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.Cloud, ConfigStorage.MiddleKey.Enemy, ConfigStorage.LowKey.GlassWings)
            nocloud_wind_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.NoCloud, ConfigStorage.MiddleKey.FieldType, ConfigStorage.LowKey.Wind)
            nocloud_floatage_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.NoCloud, ConfigStorage.MiddleKey.FieldType, ConfigStorage.LowKey.Floatage)
            nocloud_lightning_possibility -> ConfigStorage.ConfigKey(ConfigStorage.HighKey.NoCloud, ConfigStorage.MiddleKey.Danger, ConfigStorage.LowKey.Lightning)
            nocloud_tail_wind_possibility -> ConfigStorage.ConfigKey(
                ConfigStorage.HighKey.NoCloud,
                ConfigStorage.MiddleKey.Wind,
                ConfigStorage.LowKey.TailWind
            )
            nocloud_tail_side_wind_possibility -> ConfigStorage.ConfigKey(
                ConfigStorage.HighKey.NoCloud,
                ConfigStorage.MiddleKey.Wind,
                ConfigStorage.LowKey.TailSideWind
            )
            nocloud_head_wind_possibility -> ConfigStorage.ConfigKey(
                ConfigStorage.HighKey.NoCloud,
                ConfigStorage.MiddleKey.Wind,
                ConfigStorage.LowKey.HeadWind
            )
            nocloud_head_side_wind_possibility -> ConfigStorage.ConfigKey(
                ConfigStorage.HighKey.NoCloud,
                ConfigStorage.MiddleKey.Wind,
                ConfigStorage.LowKey.HeadSideWind
            )
            else -> null
        }
    }

    private fun getDependentField(seekBar: SeekBar) : TextView? {
        return when(seekBar) {
            cloud_wind_possibility -> cloud_wind_caption
            cloud_floatage_possibility -> cloud_floatage_caption
            cloud_lightning_possibility -> cloud_lighting_caption
            cloud_enemy_possibility -> cloud_enemy_caption
            cloud_zeppelin_possibility -> cloud_enemy_zeppelin_caption
            cloud_whale_possibility -> cloud_enemy_whale_caption
            cloud_spider_possibility -> cloud_enemy_spider_caption
            cloud_glasswings_possibility -> cloud_enemy_glasswings_caption
            nocloud_wind_possibility -> nocloud_wind_caption
            nocloud_floatage_possibility -> nocloud_floatage_caption
            nocloud_lightning_possibility -> nocloud_lighting_caption
            nocloud_tail_wind_possibility -> nocloud_tail_wind_caption
            nocloud_tail_side_wind_possibility -> nocloud_tail_side_wind_caption
            nocloud_head_wind_possibility -> nocloud_head_wind_caption
            nocloud_head_side_wind_possibility -> nocloud_head_side_wind_caption
            else -> null
        }
    }

    private fun getDependentDescription(seekBar: SeekBar) : Int? {
        return when(seekBar) {
            cloud_wind_possibility -> R.string.wind_possibility
            cloud_floatage_possibility -> R.string.floatage_possibility
            cloud_lightning_possibility -> R.string.lightning_possibility
            cloud_enemy_possibility -> R.string.enemy_possibility
            cloud_zeppelin_possibility -> R.string.zeppelin
            cloud_whale_possibility -> R.string.whale
            cloud_spider_possibility -> R.string.spider
            cloud_glasswings_possibility -> R.string.glasswings
            nocloud_wind_possibility -> R.string.wind_possibility
            nocloud_floatage_possibility -> R.string.floatage_possibility
            nocloud_lightning_possibility -> R.string.lightning_possibility
            nocloud_tail_wind_possibility -> R.string.tail_wind_possibility
            nocloud_tail_side_wind_possibility -> R.string.tail_side_wind_possibility
            nocloud_head_wind_possibility -> R.string.head_wind_possibility
            nocloud_head_side_wind_possibility -> R.string.head_side_wind_possibility
            else -> null
        }
    }

    private fun normalizeSeekbars(currentSeekbar: SeekBar, otherSeekbars: List<SeekBar>) {
        currentSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    var summ = progress
                    for(otherSeekbar in otherSeekbars) {
                        summ += otherSeekbar.progress
                    }
                    if(summ > Generator.PERCENT) {
                        val decrement = (summ - Generator.PERCENT)/otherSeekbars.size
                        for(otherSeekbar in otherSeekbars) {
                            otherSeekbar.progress -= decrement
                            updateSeekbarDescription(otherSeekbar, otherSeekbar.progress)
                        }
                    }
                    updateSeekbarDescription(currentSeekbar, progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
}
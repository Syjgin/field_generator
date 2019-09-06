package com.syjgin.fieldgenerator.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
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
            nocloud_lightning_possibility
        )) {
            action(i)
        }
    }

    private fun setDataForSeekbar(seekBar: SeekBar) {
        seekBar.progress =
            ConfigStorage.getConfigValue(getConfigKey(seekBar)!!, prefs)
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
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
}
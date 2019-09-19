package com.syjgin.fieldgenerator.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.syjgin.fieldgenerator.R
import com.syjgin.fieldgenerator.config.ConfigStorage
import com.syjgin.fieldgenerator.generator.Generator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cloud.setOnClickListener {
            val renderedField = GeneratedFieldRenderer.renderField(
                Generator.generateCloud(
                    ConfigStorage.getCloudConfig(
                        prefs
                    )
                ), this
            )
            showFieldDialog(renderedField)
        }
        independent.setOnClickListener {
            val renderedField = GeneratedFieldRenderer.renderField(
                Generator.generateIndependentField(
                    ConfigStorage.getFieldConfig(
                        prefs
                    )
                ), this
            )
            showFieldDialog(renderedField)
        }
        dependent.setOnClickListener {
            val renderedField =
                GeneratedFieldRenderer.renderDependentField(
                    Generator.generateDependentField(
                        ConfigStorage.getDependentFieldConfig(
                            prefs
                        )
                    ), this
                )
            showFieldDialog(renderedField)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFieldDialog(renderedField: Pair<String, Int?>) {
        val dialogBuilder = AlertDialog.Builder(this)
            .setMessage(renderedField.first)
            .setTitle(" ")
        if (renderedField.second != null) {
            dialogBuilder.setIcon(getDrawable(renderedField.second!!))
        }
        dialogBuilder.create().show()
    }
}

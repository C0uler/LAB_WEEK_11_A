package com.jordan.lab_week_11_a

import android.os.Bundle
import android.widget.Button     // Correct import for XML Views
import android.widget.EditText   // Missing in your original code
import android.widget.TextView   // Missing in your original code
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferenceWrapper = (application as SettingsApplication).settingsStore
        val preferenceViewModel = ViewModelProvider(this, object :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(preferenceWrapper) as T
            }
        })[SettingsViewModel::class.java]
        // Observe the text live data
        preferenceViewModel.textLiveData.observe(this
        ) {
        // Update the text view when the text live data changes
            findViewById<TextView>(
                R.id.activity_main_text_view
            ).text = it
        }
        findViewById<Button>(R.id.activity_main_button).setOnClickListener {
            // Save the text when the button is clicked
            preferenceViewModel.saveText(
                findViewById<EditText>(R.id.activity_main_edit_text)
                    .text.toString()
            )
        }
    }
}


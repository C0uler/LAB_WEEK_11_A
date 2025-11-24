package com.jordan.lab_week_11_a

import android.app.Application
import android.content.SharedPreferences
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PreferenceWrapper(private val sharedPreferences: SharedPreferences) {
    // The text live data is used to notify the view model when the text changes
    private val textLiveData = MutableLiveData<String>()

    init {
        // Register a listener to the shared preferences
        // The listener is called when the shared preferences change
        sharedPreferences.registerOnSharedPreferenceChangeListener {
                _, key ->
            when (key) {
                KEY_TEXT -> {
                    // Notify the view model that the text has changed
                    // The view model will then notify the activity
                    textLiveData.postValue(    sharedPreferences
                        .getString(KEY_TEXT, "")
                    )
                }
            }
        }
    }

    // Save the text to the shared preferences
    fun saveText(text: String) {
        sharedPreferences.edit()
            .putString(KEY_TEXT, text)
            .apply()
    }

    // Get the text from the shared preferences
    fun getText(): LiveData<String> {
        textLiveData.postValue(sharedPreferences.getString(KEY_TEXT, ""))
        return textLiveData
    }

    // The key used to store the text in the shared preferences
    companion object{
        const val KEY_TEXT = "keyText"
    }
}



class PreferenceApplication : Application() {
    lateinit var preferenceWrapper: PreferenceWrapper
    override fun onCreate() {
        super.onCreate()

        // Initialize the preference wrapper
        // The preference wrapper is used to access the shared preferences
        preferenceWrapper = PreferenceWrapper(
            // Get the shared preferences
            // The shared preferences are stored in the file
            // /data/data/com.example.lab_week_11_a/shared_prefs/prefs.xml
            getSharedPreferences( // The name of the file
                "prefs",
                // The mode of the file
                // MODE_PRIVATE means that only this application can access the file
                Context.MODE_PRIVATE
            )
        )
    }
}
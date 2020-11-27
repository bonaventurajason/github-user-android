package com.bonaventurajason.githubuser.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.bonaventurajason.githubuser.R
import com.bonaventurajason.githubuser.data.service.ReminderAlarmHelper
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_ID_REPEATING
import com.bumptech.glide.Glide.init
import java.util.*


class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var REMINDER: String
    private lateinit var LANGUAGE: String

    private lateinit var reminderPreference: SwitchPreferenceCompat
    private lateinit var languagePreference: Preference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_preferences)
        init()
        setSummaries()
    }


    private fun init() {
        REMINDER = resources.getString(R.string.reminder_key)
        LANGUAGE = resources.getString(R.string.language_key)

        reminderPreference =
            findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
        languagePreference =
            findPreference<Preference>(LANGUAGE) as Preference

        setReminder()
        setLanguage()

    }

    private fun setLanguage() {
        languagePreference.setOnPreferenceClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences

        reminderPreference.isChecked = sh.getBoolean(REMINDER, false)

    }

    private fun setReminder() {
        reminderPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                if (reminderPreference.isChecked) {
                    reminderPreference.isChecked = false
                    cancelAlarmManager()
                } else {
                    reminderPreference.isChecked = true
                    setAlarmManager()
                }
                false
            }
    }

    private fun cancelAlarmManager() {
        context?.let {
            ReminderAlarmHelper.stopAlarm(
                it,
                REMINDER_ID_REPEATING
            )
        }
        Toast.makeText(requireContext(), "Daily reminder has successfully turned off ", Toast.LENGTH_LONG)
            .show()
    }

    private fun setAlarmManager() {
        context?.let {
            ReminderAlarmHelper.setAlarm(
                it,
                getString(R.string.app_name),
                getString(R.string.notification_message),
                REMINDER_ID_REPEATING,
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 9)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }

            )
        }
        Toast.makeText(requireContext(), "Daily reminder has successfully turned on ", Toast.LENGTH_LONG)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == REMINDER) {
            if (sharedPreferences != null) {
                reminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)
            }
        }
    }


}
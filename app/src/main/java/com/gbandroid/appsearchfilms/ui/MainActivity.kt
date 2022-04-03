package com.gbandroid.appsearchfilms.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.databinding.ActivityMainBinding
import com.gbandroid.appsearchfilms.util.MyConnectReceiver
import com.gbandroid.appsearchfilms.util.showSnackBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
const val APP_SETTING = "APP_SETTING"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val receiver = MyConnectReceiver()
    private var isChecked by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.app_title)
        isChecked = getSettings()
        initToolbar()
        initNavigation()
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initNavigation() {
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                binding.root.showSnackBar(getString(R.string.sort))
                true
            }
            R.id.action_favorite -> {
                binding.root.showSnackBar(getString(R.string.favorite))
                true
            }
            R.id.action_setting -> {
                isChecked = !item.isChecked
                item.setChecked(isChecked)
                saveSettings(isChecked)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu!!.findItem(R.id.action_search)
        val settingFilter = menu.findItem(R.id.action_setting)

        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.root.showSnackBar(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        settingFilter.setChecked(isChecked)

        return true
    }

    private fun saveSettings(value: Boolean) {
        getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
            .edit()
            .putBoolean(APP_SETTING, value)
            .apply()
    }

    private fun getSettings() = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        .getBoolean(APP_SETTING, false)

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}

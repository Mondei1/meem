package dev.klier.meem.activities.setup

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.color.DynamicColors
import dev.klier.meem.DatabaseViewModel
import dev.klier.meem.R
import dev.klier.meem.activities.main.MainActivity
import dev.klier.meem.databinding.ActivityStartupBinding

class StartupActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
        prefs = getSharedPreferences(application.packageName, MODE_PRIVATE)

        // Redirect to main activity as app has been already set up.
        if (prefs.getInt("last_version", -1) != -1) {
            this.startActivity(Intent(this, MainActivity::class.java))
            return
        }

        binding = ActivityStartupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DynamicColors.applyToActivitiesIfAvailable(application)

        val db = ViewModelProvider(this)[DatabaseViewModel::class.java]
        db.init(this)

        //setSupportActionBar(binding.toolbar)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
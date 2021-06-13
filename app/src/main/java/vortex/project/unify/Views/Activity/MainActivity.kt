package vortex.project.unify.Views.Activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R
import vortex.project.unify.Views.ViewModel.PreferencesViewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var preferencesViewModel: PreferencesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)

        preferencesViewModel = ViewModelProviders.of(this).get(PreferencesViewModel::class.java)

        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.display_fragments) as NavHostFragment? ?: return
        val navController = host.navController


        MobileAds.initialize(this) {}

        setupBottomNavMenu(navController)

        bottom_nav_view!!.showOrInvisible(false)

        changeTheme()

        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.home_dest,
                        R.id.profile_dest,
                        R.id.likes_dest,
                        R.id.search_dest,
                        R.id.new_fallowers_dest,
                        R.id.myPets_dest,
                        R.id.loginFragment
                )
        )

        setupActionBar(navController, appBarConfiguration)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.top_bar_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return  item.onNavDestinationSelected(findNavController(R.id.display_fragments))
//    }

    private fun setupActionBar(navController: NavController, appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.display_fragments).navigateUp(appBarConfiguration)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.display_fragments))
                || super.onOptionsItemSelected(item)
    }

    private fun changeTheme() {
        preferencesViewModel.modeNight.observe(this, Observer {
            if (it == getString(R.string.day_mode)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        })
    }

    fun View.showOrInvisible(show: Boolean) {
        visibility = if (show) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}
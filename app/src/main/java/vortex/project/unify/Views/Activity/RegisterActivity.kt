package vortex.project.unify.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R

class RegisterActivity : AppCompatActivity() {
//    private lateinit var navController: NavController
//    private lateinit var regDrawerLayout: DrawerLayout
//    private lateinit var appBarConfiguration: AppBarConfiguration
//
//    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.register_navigation) as NavHostFragment? ?: return
//
//        setSupportActionBar(toolbar)
//
//        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.register_navigation) as NavHostFragment? ?: return
//
//        navController = findNavController(R.id.register_fragment)
//        regDrawerLayout = findViewById(R.id.reg_drawer_layout)
//        appBarConfiguration = AppBarConfiguration(navController.graph, regDrawerLayout)
//
//        navigationView.setupWithNavController(navController)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        listener = NavController.OnDestinationChangedListener {controller, destination, arguments ->
//            if (destination.id == R.id.reg_email){
//                supportActionBar?.setDisplayShowTitleEnabled(true)
//                supportActionBar!!.title = "E-Mail"
//            } else if (destination.id == R.id.reg_phone){
//                supportActionBar?.setDisplayShowTitleEnabled(true)
//                supportActionBar!!.title = "Phone"
//            } else if (destination.id == R.id.reg_password){
//                supportActionBar?.setDisplayShowTitleEnabled(true)
//                supportActionBar!!.title = "Password"
//            }
//        }
    }
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.register_fragment)
//        return navController.navigateUp(appBarConfiguration)
//                ||super.onSupportNavigateUp()
//    }
}
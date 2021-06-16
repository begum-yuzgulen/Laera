package com.yuzgulen.laera

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.yuzgulen.laera.utils.App
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        lateinit var instance: App private set
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val fragmentManager = supportFragmentManager
            val newFragment = SendFeedback()
            newFragment.show(fragmentManager, "dialog")

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_exercise, R.id.nav_profile,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null){
            changeNavigationHeaderInfo(user.displayName.toString(), user.photoUrl.toString() )
        }
    }

    private fun changeNavigationHeaderInfo(name :String, profilePic:String?=null, defaultProfile:Boolean=false) {
        val header = nav_view.getHeaderView(0)

        if(profilePic!=null) {
            Picasso.get().load(profilePic).into(header.nav_header_pic)
        }
        else if(defaultProfile){
            header.nav_header_pic.setImageResource(R.mipmap.ic_launcher)
        }
        header.nav_header_text.text = name
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}


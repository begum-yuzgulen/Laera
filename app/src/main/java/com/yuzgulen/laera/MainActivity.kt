package com.yuzgulen.laera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.yuzgulen.laera.domain.usecases.IsAdmin
import com.yuzgulen.laera.ui.home.GeneratePDF
import com.yuzgulen.laera.utils.App
import com.yuzgulen.laera.utils.ICallback
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
        val v = setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val fragmentManager = supportFragmentManager
            val newFragment = SendFeedbackDialog()
            newFragment.show(fragmentManager, "dialog")

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_exercise, R.id.nav_profile,
                R.id.nav_tools, R.id.nav_rules, R.id.nav_about, R.id.nav_admin
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null) {
            IsAdmin.getInstance().execute(user.uid, object: ICallback<Boolean> {
                override fun onCallback(value: Boolean) {
                    if(!value) {
                        navView.menu.findItem(R.id.nav_admin).isVisible = false
                    }
                }

            })
            changeNavigationHeaderInfo(user.displayName.toString(), user.photoUrl.toString() )
        }
    }

    private fun changeNavigationHeaderInfo(name :String, profilePic:String?=null, defaultProfile:Boolean=false) {
        val header = nav_view.getHeaderView(0)

        if(profilePic!=null) {
            Picasso.get().load(profilePic).into(header.nav_header_pic)
        }
        else if(defaultProfile){
            header.nav_header_pic.setImageResource(R.drawable.ic_profile)
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

    var tag: String = ""
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.e("Permission: ", "Granted")
                GeneratePDF().pdfGeneration(tag)
            } else {
                Log.e("Permission: ", "Denied")
            }
        }

    fun onClickRequestPermission(view: View) {
        tag = view.tag.toString()
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                GeneratePDF().pdfGeneration(tag)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }

    }

}


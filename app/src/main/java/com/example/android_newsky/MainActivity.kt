package com.example.android_newsky

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android_newsky.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Pack200

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        text_Start.setOnClickListener {
            startActivity(Intent(this, Text_view::class.java))
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_home ->{
                
            }
            R.id.action_check ->{
                var checkViewFragment = CheckViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,checkViewFragment).commit()
                return true
            }
//            R.id.action_chat->{
//                var detailViewFragment = DetailViewFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFragment).commit()
//                return true
//            }
            R.id.action_chat ->{
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(Intent(this, AddPhotoActivity::class.java))
                }
            }
            R.id.action_setting ->{
                var setViewFragment = SetViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,setViewFragment).commit()
                return true
            }
            R.id.action_account ->{
                var userViewFragment = UserViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,userViewFragment).commit()
                return true
            }
        }
        return false
    }
    fun moveMainPage(){
        startActivity(Intent(this, MainActivity::class.java))


    }
}

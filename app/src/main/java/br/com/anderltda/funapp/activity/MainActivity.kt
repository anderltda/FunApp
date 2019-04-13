package br.com.anderltda.funapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newFragment(HomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragmentManager = supportFragmentManager
        if(fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView
        .OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                newFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                newFragment(BlankFragment.newInstance("Dashbord"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                newFragment(BlankFragment.newInstance("Notifications"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_talk -> {

                //val next = Intent(this, ListaActivity::class.java)
               // startActivity(next)
                //finish()
                newFragment(TalkFragment.newInstance())


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_setting -> {
                newFragment(SettingFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun newFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flContainer, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}

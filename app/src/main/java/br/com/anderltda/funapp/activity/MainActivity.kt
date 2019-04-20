package br.com.anderltda.funapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newFragment(ContactFragment.newInstance())
        navigation.selectedItemId = getSelectedItem(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun getSelectedItem(bottomNavigationView: BottomNavigationView): Int {
        val menu = bottomNavigationView.menu
        for (i in 0 until bottomNavigationView.menu.size()) {
            val menuItem = menu.getItem(i)
            if (menuItem.title == resources.getString(R.string.title_chat)) {
                return menuItem.itemId
            }
        }
        return 0
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
            R.id.navigation_chat -> {
                newFragment(ContactFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_location -> {
                newFragment(LocationFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_person -> {
                newFragment(PersonFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_setting -> {
                newFragment(SettingFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                newFragment(AboutFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun newFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}
